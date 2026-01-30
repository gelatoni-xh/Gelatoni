package com.csxuhuan.gelatoni.application.service;

import com.csxuhuan.gelatoni.application.dto.MatchGameStatsDTO;
import com.csxuhuan.gelatoni.domain.model.entity.MatchPlayerStats;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 比赛数据统计计算器
 *
 * <p>职责：
 * <ul>
 *     <li>输入：筛选范围内的我方球员明细数据（team_type=1）</li>
 *     <li>输出：按指标生成榜单，并按规则排序</li>
 * </ul>
 *
 * <p>设计约束：
 * <ul>
 *     <li>本期不做预计算/缓存/中间表</li>
 *     <li>所有榜单由一次查询 + 内存聚合得到</li>
 *     <li>计算逻辑保持简单、清晰、可读</li>
 * </ul>
 */
public class MatchGameStatsCalculator {

    private MatchGameStatsCalculator() {
    }

    /**
     * 计算统计结果
     *
     * @param season 赛季（为空表示全赛季，仅用于回显）
     * @param dimension 统计维度
     * @param myPlayerStats 我方球员统计明细（team_type=1）
     * @return 统计结果 DTO
     */
    public static MatchGameStatsDTO calculate(String season, MatchGameStatsDTO.Dimension dimension,
                                             List<MatchPlayerStats> myPlayerStats) {
        List<MatchPlayerStats> source = myPlayerStats == null ? new ArrayList<>() : myPlayerStats;

        Map<String, Accumulator> group = new HashMap<>();
        for (MatchPlayerStats stat : source) {
            String key = dimension == MatchGameStatsDTO.Dimension.PLAYER
                    ? normalizeKey(stat.getPlayerName())
                    : normalizeKey(stat.getUserName());

            Accumulator acc = group.computeIfAbsent(key, k -> new Accumulator());
            acc.appearances++;

            acc.score += safeInt(stat.getScore());
            acc.rebound += safeInt(stat.getRebound());
            acc.assist += safeInt(stat.getAssist());
            acc.steal += safeInt(stat.getSteal());
            acc.block += safeInt(stat.getBlock());
            acc.turnover += safeInt(stat.getTurnover());

            acc.fgAttempt += safeInt(stat.getFgAttempt());
            acc.fgMade += safeInt(stat.getFgMade());
            acc.threeAttempt += safeInt(stat.getThreeAttempt());
            acc.threeMade += safeInt(stat.getThreeMade());

            if (Boolean.TRUE.equals(stat.getIsMvp())) {
                acc.mvp++;
            }
            if (Boolean.TRUE.equals(stat.getIsSvp())) {
                acc.svp++;
            }
        }

        List<MatchGameStatsDTO.Leaderboard> leaderboards = new ArrayList<>();

        if (dimension == MatchGameStatsDTO.Dimension.PLAYER) {
            leaderboards.add(buildValueLeaderboard(MatchGameStatsDTO.Metric.APPEARANCES, group, a -> a.appearances));
        }

        leaderboards.add(buildValueLeaderboard(MatchGameStatsDTO.Metric.SCORE, group, a -> a.score));
        leaderboards.add(buildValueLeaderboard(MatchGameStatsDTO.Metric.REBOUND, group, a -> a.rebound));
        leaderboards.add(buildValueLeaderboard(MatchGameStatsDTO.Metric.ASSIST, group, a -> a.assist));
        leaderboards.add(buildValueLeaderboard(MatchGameStatsDTO.Metric.STEAL, group, a -> a.steal));
        leaderboards.add(buildValueLeaderboard(MatchGameStatsDTO.Metric.BLOCK, group, a -> a.block));

        leaderboards.add(buildValueLeaderboard(MatchGameStatsDTO.Metric.FG_ATTEMPT, group, a -> a.fgAttempt));
        leaderboards.add(buildValueLeaderboard(MatchGameStatsDTO.Metric.FG_MADE, group, a -> a.fgMade));
        leaderboards.add(buildRateLeaderboard(MatchGameStatsDTO.Metric.FG_PCT, group, a -> a.fgMade, a -> a.fgAttempt));

        leaderboards.add(buildValueLeaderboard(MatchGameStatsDTO.Metric.THREE_ATTEMPT, group, a -> a.threeAttempt));
        leaderboards.add(buildValueLeaderboard(MatchGameStatsDTO.Metric.THREE_MADE, group, a -> a.threeMade));
        leaderboards.add(buildRateLeaderboard(MatchGameStatsDTO.Metric.THREE_PCT, group, a -> a.threeMade, a -> a.threeAttempt));

        leaderboards.add(buildValueLeaderboard(MatchGameStatsDTO.Metric.MVP, group, a -> a.mvp));
        leaderboards.add(buildValueLeaderboard(MatchGameStatsDTO.Metric.SVP, group, a -> a.svp));
        leaderboards.add(buildValueLeaderboard(MatchGameStatsDTO.Metric.TURNOVER, group, a -> a.turnover));

        return new MatchGameStatsDTO(season, dimension, leaderboards);
    }

    private static String normalizeKey(String key) {
        if (key == null) {
            return "UNKNOWN";
        }
        String trimmed = key.trim();
        return trimmed.isEmpty() ? "UNKNOWN" : trimmed;
    }

    private static long safeInt(Integer value) {
        return value == null ? 0L : value.longValue();
    }

    private interface LongGetter {
        long get(Accumulator acc);
    }

    private static MatchGameStatsDTO.Leaderboard buildValueLeaderboard(MatchGameStatsDTO.Metric metric,
                                                                       Map<String, Accumulator> group,
                                                                       LongGetter getter) {
        List<MatchGameStatsDTO.RankItem> items = group.entrySet().stream()
                .map(e -> new MatchGameStatsDTO.RankItem(e.getKey(), getter.get(e.getValue())))
                .sorted(Comparator.comparing(MatchGameStatsDTO.RankItem::getValue, Comparator.nullsLast(Comparator.naturalOrder())).reversed()
                        .thenComparing(MatchGameStatsDTO.RankItem::getName))
                .collect(Collectors.toList());

        return new MatchGameStatsDTO.Leaderboard(metric, items);
    }

    private static MatchGameStatsDTO.Leaderboard buildRateLeaderboard(MatchGameStatsDTO.Metric metric,
                                                                      Map<String, Accumulator> group,
                                                                      LongGetter madeGetter,
                                                                      LongGetter attemptGetter) {
        List<MatchGameStatsDTO.RankItem> items = group.entrySet().stream()
                .map(e -> {
                    long made = madeGetter.get(e.getValue());
                    long attempt = attemptGetter.get(e.getValue());
                    double rate = attempt <= 0 ? 0D : (double) made / (double) attempt;
                    return new MatchGameStatsDTO.RankItem(e.getKey(), made, attempt, rate);
                })
                .sorted(Comparator
                        .comparing((MatchGameStatsDTO.RankItem i) -> i.getRate() != null ? i.getRate() : 0D, Comparator.naturalOrder())
                        .reversed()
                        .thenComparing(i -> i.getAttempt() != null ? i.getAttempt() : 0L, Comparator.reverseOrder())
                        .thenComparing(i -> i.getMade() != null ? i.getMade() : 0L, Comparator.reverseOrder())
                        .thenComparing(MatchGameStatsDTO.RankItem::getName))
                .collect(Collectors.toList());

        return new MatchGameStatsDTO.Leaderboard(metric, items);
    }

    /**
     * 聚合器：用于累加统计指标
     */
    private static class Accumulator {
        long appearances;

        long score;
        long rebound;
        long assist;
        long steal;
        long block;

        long fgAttempt;
        long fgMade;

        long threeAttempt;
        long threeMade;

        long mvp;
        long svp;

        long turnover;
    }
}
