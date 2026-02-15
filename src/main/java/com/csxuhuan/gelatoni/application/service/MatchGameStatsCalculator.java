package com.csxuhuan.gelatoni.application.service;

import com.csxuhuan.gelatoni.application.dto.MatchGameStatsDTO;
import com.csxuhuan.gelatoni.domain.model.entity.MatchPlayerStats;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 比赛数据统计计算器
 *
 * <p>核心职责：
 * <ul>
 *     <li>输入：筛选范围内的我方球员明细数据（team_type=1）</li>
 *     <li>处理：按统计维度进行数据聚合和计算</li>
 *     <li>输出：按各项指标生成排行榜，并按照既定规则进行排序</li>
 * </ul>
 *
 * <p>功能特性：
 * <ul>
 *     <li>支持PLAYER和USER两种统计维度</li>
 *     <li>提供15种不同的统计指标计算</li>
 *     <li>自动处理空值和异常数据</li>
 *     <li>支持命中率类指标的精确计算</li>
 * </ul>
 *
 * <p>设计原则：
 * <ul>
 *     <li>本期采用实时计算模式，不做预计算/缓存/中间表</li>
 *     <li>所有榜单通过一次数据库查询 + 内存聚合计算得出</li>
 *     <li>计算逻辑保持简洁、清晰、易于维护</li>
 *     <li>排序规则明确且一致</li>
 * </ul>
 *
 * <p>排序规则：
 * <ul>
 *     <li>数值类榜单：按数值降序排列，相同数值按名称升序</li>
 *     <li>命中率类榜单：按命中率降序，相同命中率按出手次数降序，再相同按命中数降序，最后按名称升序</li>
 * </ul>
 */
public class MatchGameStatsCalculator {

    private MatchGameStatsCalculator() {
    }

    /**
     * 核心计算方法：生成比赛统计数据榜单
     *
     * <p>执行流程：
     * <ol>
     *     <li>数据预处理：处理输入数据，建立统计维度映射</li>
     *     <li>数据聚合：按指定维度对各项指标进行累加统计</li>
     *     <li>榜单构建：为每个统计指标生成对应的排行榜</li>
     *     <li>结果封装：将所有榜单组装成最终的DTO返回</li>
     * </ol>
     *
     * @param season 赛季标识（为空表示全赛季统计，仅用于结果回显）
     * @param dimension 统计维度（PLAYER-球员维度，USER-用户维度）
     * @param myPlayerStats 我方球员统计明细数据列表（要求team_type=1）
     * @return 完整的比赛统计数据DTO，包含所有指标的排行榜
     * @throws IllegalArgumentException 当输入参数不符合要求时抛出
     */
    public static MatchGameStatsDTO calculate(String season, MatchGameStatsDTO.Dimension dimension,
                                             List<MatchPlayerStats> myPlayerStats) {
        List<MatchPlayerStats> source = myPlayerStats == null ? new ArrayList<>() : myPlayerStats;

        // 数据聚合阶段：按统计维度分组并累加各项指标
        Map<String, Accumulator> group = new HashMap<>();
        for (MatchPlayerStats stat : source) {
            // 根据统计维度确定分组键
            String key = dimension == MatchGameStatsDTO.Dimension.PLAYER
                    ? normalizeKey(stat.getPlayerName())
                    : normalizeKey(stat.getUserName());

            // 获取或创建该分组的累加器
            Accumulator acc = group.computeIfAbsent(key, k -> new Accumulator());
            
            // 基础统计指标累加
            acc.appearances++; // 参与场次
            acc.score += safeInt(stat.getScore()); // 得分
            acc.rebound += safeInt(stat.getRebound()); // 篮板
            acc.assist += safeInt(stat.getAssist()); // 助攻
            acc.steal += safeInt(stat.getSteal()); // 抢断
            acc.block += safeInt(stat.getBlock()); // 盖帽
            acc.turnover += safeInt(stat.getTurnover()); // 失误

            // 投篮相关统计累加
            acc.fgAttempt += safeInt(stat.getFgAttempt()); // 投篮出手
            acc.fgMade += safeInt(stat.getFgMade()); // 投篮命中
            acc.threeAttempt += safeInt(stat.getThreeAttempt()); // 三分出手
            acc.threeMade += safeInt(stat.getThreeMade()); // 三分命中

            // MVP/SVP荣誉统计
            if (Boolean.TRUE.equals(stat.getIsMvp())) {
                acc.mvp++;
            }
            if (Boolean.TRUE.equals(stat.getIsSvp())) {
                acc.svp++;
            }
        }

        // 榜单构建阶段：为每个统计指标生成对应的排行榜
        List<MatchGameStatsDTO.Leaderboard> leaderboards = new ArrayList<>();

        // PLAYER维度特有指标
        if (dimension == MatchGameStatsDTO.Dimension.PLAYER) {
            leaderboards.add(buildValueLeaderboard(MatchGameStatsDTO.Metric.APPEARANCES, group, a -> a.appearances));
        }

        // 基础数据榜单（数值越大排名越前）
        leaderboards.add(buildValueLeaderboard(MatchGameStatsDTO.Metric.SCORE, group, a -> a.score));
        leaderboards.add(buildAvgLeaderboard(MatchGameStatsDTO.Metric.SCORE_AVG, group, a -> a.score));
        leaderboards.add(buildValueLeaderboard(MatchGameStatsDTO.Metric.REBOUND, group, a -> a.rebound));
        leaderboards.add(buildAvgLeaderboard(MatchGameStatsDTO.Metric.REBOUND_AVG, group, a -> a.rebound));
        leaderboards.add(buildValueLeaderboard(MatchGameStatsDTO.Metric.ASSIST, group, a -> a.assist));
        leaderboards.add(buildAvgLeaderboard(MatchGameStatsDTO.Metric.ASSIST_AVG, group, a -> a.assist));
        leaderboards.add(buildValueLeaderboard(MatchGameStatsDTO.Metric.STEAL, group, a -> a.steal));
        leaderboards.add(buildAvgLeaderboard(MatchGameStatsDTO.Metric.STEAL_AVG, group, a -> a.steal));
        leaderboards.add(buildValueLeaderboard(MatchGameStatsDTO.Metric.BLOCK, group, a -> a.block));
        leaderboards.add(buildAvgLeaderboard(MatchGameStatsDTO.Metric.BLOCK_AVG, group, a -> a.block));

        // 投篮数据榜单
        leaderboards.add(buildValueLeaderboard(MatchGameStatsDTO.Metric.FG_ATTEMPT, group, a -> a.fgAttempt));
        leaderboards.add(buildAvgLeaderboard(MatchGameStatsDTO.Metric.FG_ATTEMPT_AVG, group, a -> a.fgAttempt));
        leaderboards.add(buildValueLeaderboard(MatchGameStatsDTO.Metric.FG_MADE, group, a -> a.fgMade));
        leaderboards.add(buildAvgLeaderboard(MatchGameStatsDTO.Metric.FG_MADE_AVG, group, a -> a.fgMade));
        // 投篮命中率榜单（特殊处理：按命中率排序）
        leaderboards.add(buildRateLeaderboard(MatchGameStatsDTO.Metric.FG_PCT, group, a -> a.fgMade, a -> a.fgAttempt));
        leaderboards.add(buildAvgRateLeaderboard(MatchGameStatsDTO.Metric.FG_PCT_AVG, group, a -> a.fgMade, a -> a.fgAttempt));

        // 三分球数据榜单
        leaderboards.add(buildValueLeaderboard(MatchGameStatsDTO.Metric.THREE_ATTEMPT, group, a -> a.threeAttempt));
        leaderboards.add(buildAvgLeaderboard(MatchGameStatsDTO.Metric.THREE_ATTEMPT_AVG, group, a -> a.threeAttempt));
        leaderboards.add(buildValueLeaderboard(MatchGameStatsDTO.Metric.THREE_MADE, group, a -> a.threeMade));
        leaderboards.add(buildAvgLeaderboard(MatchGameStatsDTO.Metric.THREE_MADE_AVG, group, a -> a.threeMade));
        // 三分命中率榜单（特殊处理：按命中率排序）
        leaderboards.add(buildRateLeaderboard(MatchGameStatsDTO.Metric.THREE_PCT, group, a -> a.threeMade, a -> a.threeAttempt));
        leaderboards.add(buildAvgRateLeaderboard(MatchGameStatsDTO.Metric.THREE_PCT_AVG, group, a -> a.threeMade, a -> a.threeAttempt));

        // 荣誉榜单
        leaderboards.add(buildValueLeaderboard(MatchGameStatsDTO.Metric.MVP, group, a -> a.mvp));
        leaderboards.add(buildAvgLeaderboard(MatchGameStatsDTO.Metric.MVP_AVG, group, a -> a.mvp));
        leaderboards.add(buildValueLeaderboard(MatchGameStatsDTO.Metric.SVP, group, a -> a.svp));
        leaderboards.add(buildAvgLeaderboard(MatchGameStatsDTO.Metric.SVP_AVG, group, a -> a.svp));
        
        // 失误榜单（数值越小越好，但按数值降序排列）
        leaderboards.add(buildValueLeaderboard(MatchGameStatsDTO.Metric.TURNOVER, group, a -> a.turnover));
        leaderboards.add(buildAvgLeaderboard(MatchGameStatsDTO.Metric.TURNOVER_AVG, group, a -> a.turnover));

        return new MatchGameStatsDTO(season, dimension, leaderboards);
    }

    /**
     * 标准化分组键
     * <p>处理可能的空值和空白字符，确保分组键的一致性
     *
     * @param key 原始键值（可能为null或空白）
     * @return 标准化后的键值，空值统一返回"UNKNOWN"
     */
    private static String normalizeKey(String key) {
        if (key == null) {
            return "UNKNOWN";
        }
        String trimmed = key.trim();
        return trimmed.isEmpty() ? "UNKNOWN" : trimmed;
    }

    /**
     * 安全的整数转换
     * <p>处理可能的空值，避免空指针异常
     *
     * @param value 可能为null的Integer值
     * @return 转换后的long值，null值返回0
     */
    private static long safeInt(Integer value) {
        return value == null ? 0L : value.longValue();
    }

    /**
     * 长整型获取器函数式接口
     * <p>用于从累加器中提取特定指标的数值
     */
    private interface LongGetter {
        /**
         * 从累加器中获取长整型数值
         * @param acc 累加器对象
         * @return 对应指标的数值
         */
        long get(Accumulator acc);
    }

    /**
     * 构建数值型榜单
     * <p>用于处理得分、篮板、助攻等直接数值类统计指标
     *
     * @param metric 统计指标类型
     * @param group 分组后的统计数据映射
     * @param getter 数值获取器，用于从累加器中提取对应指标值
     * @return 构建完成的榜单对象
     */
    private static MatchGameStatsDTO.Leaderboard buildValueLeaderboard(MatchGameStatsDTO.Metric metric,
                                                                       Map<String, Accumulator> group,
                                                                       LongGetter getter) {
        List<MatchGameStatsDTO.RankItem> items = group.entrySet().stream()
                .map(e -> new MatchGameStatsDTO.RankItem(e.getKey(), getter.get(e.getValue())))
                // 排序规则：按数值降序，相同数值按名称升序
                .sorted(Comparator.comparing(MatchGameStatsDTO.RankItem::getValue, Comparator.nullsLast(Comparator.naturalOrder())).reversed()
                        .thenComparing(MatchGameStatsDTO.RankItem::getName))
                .collect(Collectors.toList());

        return new MatchGameStatsDTO.Leaderboard(metric, metric.getDesc() + "榜", items);
    }

    /**
     * 构建场均值型榜单
     * <p>用于处理场均得分、场均篮板、场均助攻等场均类统计指标
     * <p>计算逻辑：场均值 = 总数值 / 上场次数，保留一位小数
     * <p>排序规则：按场均值降序排列，相同场均值按名称升序
     *
     * @param metric 统计指标类型（如SCORE_AVG、REBOUND_AVG等）
     * @param group 分组后的统计数据映射（key为球员/用户名，value为累加器）
     * @param getter 数值获取器，用于从累加器中提取对应指标的总数值
     * @return 构建完成的场均榜单对象
     */
    private static MatchGameStatsDTO.Leaderboard buildAvgLeaderboard(MatchGameStatsDTO.Metric metric,
                                                                     Map<String, Accumulator> group,
                                                                     LongGetter getter) {
        List<MatchGameStatsDTO.RankItem> items = group.entrySet().stream()
                .map(e -> {
                    // 获取该球员/用户的总数值（如总得分、总篮板等）
                    long total = getter.get(e.getValue());
                    // 获取上场次数
                    long appearances = e.getValue().appearances;
                    // 计算场均值：总数值 / 上场次数，保留一位小数
                    // 使用 Math.round 进行四舍五入：先乘以10，四舍五入后再除以10
                    double avg = appearances <= 0 ? 0D : Math.round((double) total / appearances * 10.0) / 10.0;
                    // 创建榜单条目，只包含名称和场均值
                    MatchGameStatsDTO.RankItem item = new MatchGameStatsDTO.RankItem(e.getKey(), avg);
                    return item;
                })
                // 排序规则：按场均值降序（数值越大排名越前），相同场均值按名称升序
                .sorted(Comparator.comparing((MatchGameStatsDTO.RankItem i) -> i.getAvg() != null ? i.getAvg() : 0D, Comparator.naturalOrder()).reversed()
                        .thenComparing(MatchGameStatsDTO.RankItem::getName))
                .collect(Collectors.toList());

        return new MatchGameStatsDTO.Leaderboard(metric, metric.getDesc() + "榜", items);
    }

    /**
     * 构建场均命中率型榜单
     * <p>用于处理场均投篮命中率、场均三分命中率等场均比率类统计指标
     * <p>计算逻辑：
     * <ul>
     *     <li>场均命中数 = 总命中数 / 上场次数，保留一位小数</li>
     *     <li>场均出手数 = 总出手数 / 上场次数，保留一位小数</li>
     *     <li>整体命中率 = 总命中数 / 总出手数（不是场均命中率）</li>
     * </ul>
     * <p>排序规则：按整体命中率降序，相同时按场均出手降序，再相同按场均命中降序，最后按名称升序
     *
     * @param metric 统计指标类型（如FG_PCT_AVG、THREE_PCT_AVG等）
     * @param group 分组后的统计数据映射（key为球员/用户名，value为累加器）
     * @param madeGetter 命中数获取器，用于从累加器中提取总命中数
     * @param attemptGetter 出手数获取器，用于从累加器中提取总出手数
     * @return 构建完成的场均命中率榜单对象
     */
    private static MatchGameStatsDTO.Leaderboard buildAvgRateLeaderboard(MatchGameStatsDTO.Metric metric,
                                                                         Map<String, Accumulator> group,
                                                                         LongGetter madeGetter,
                                                                         LongGetter attemptGetter) {
        List<MatchGameStatsDTO.RankItem> items = group.entrySet().stream()
                .map(e -> {
                    // 获取总命中数和总出手数
                    long totalMade = madeGetter.get(e.getValue());
                    long totalAttempt = attemptGetter.get(e.getValue());
                    // 获取上场次数
                    long appearances = e.getValue().appearances;
                    // 计算场均命中数：总命中数 / 上场次数，保留一位小数
                    double avgMade = appearances <= 0 ? 0D : Math.round((double) totalMade / appearances * 10.0) / 10.0;
                    // 计算场均出手数：总出手数 / 上场次数，保留一位小数
                    double avgAttempt = appearances <= 0 ? 0D : Math.round((double) totalAttempt / appearances * 10.0) / 10.0;
                    // 计算整体命中率：总命中数 / 总出手数（注意：不是场均命中率）
                    double rate = totalAttempt <= 0 ? 0D : (double) totalMade / (double) totalAttempt;
                    // 创建榜单条目，包含场均命中、场均出手和整体命中率
                    // 注意：made和attempt字段存储的是场均值（Double类型，保留一位小数）
                    MatchGameStatsDTO.RankItem item = new MatchGameStatsDTO.RankItem(e.getKey(), avgMade, avgAttempt, rate);
                    return item;
                })
                // 复杂排序规则：
                // 1. 按整体命中率降序（命中率越高排名越前）
                // 2. 相同命中率时按场均出手次数降序（体现稳定性）
                // 3. 相同场均出手时按场均命中数降序（体现绝对能力）
                // 4. 最后按名称升序（保证排序一致性）
                .sorted(Comparator
                        .comparing((MatchGameStatsDTO.RankItem i) -> i.getRate() != null ? i.getRate() : 0D, Comparator.naturalOrder())
                        .reversed()
                        .thenComparing(i -> i.getAttempt() != null ? i.getAttempt() : 0L, Comparator.reverseOrder())
                        .thenComparing(i -> i.getMade() != null ? i.getMade() : 0L, Comparator.reverseOrder())
                        .thenComparing(MatchGameStatsDTO.RankItem::getName))
                .collect(Collectors.toList());

        return new MatchGameStatsDTO.Leaderboard(metric, metric.getDesc() + "榜", items);
    }

    /**
     * 构建命中率型榜单
     * <p>专门用于处理投篮命中率、三分命中率等比率类统计指标
     * <p>排序规则：命中率优先，相同时考虑出手次数和命中数
     *
     * @param metric 统计指标类型
     * @param group 分组后的统计数据映射
     * @param madeGetter 命中数获取器
     * @param attemptGetter 出手数获取器
     * @return 构建完成的榜单对象
     */
    private static MatchGameStatsDTO.Leaderboard buildRateLeaderboard(MatchGameStatsDTO.Metric metric,
                                                                      Map<String, Accumulator> group,
                                                                      LongGetter madeGetter,
                                                                      LongGetter attemptGetter) {
        List<MatchGameStatsDTO.RankItem> items = group.entrySet().stream()
                .map(e -> {
                    long made = madeGetter.get(e.getValue());
                    long attempt = attemptGetter.get(e.getValue());
                    // 命中率计算：避免除零错误
                    double rate = attempt <= 0 ? 0D : (double) made / (double) attempt;
                    return new MatchGameStatsDTO.RankItem(e.getKey(), (double) made, (double) attempt, rate);
                })
                // 复杂排序规则：
                // 1. 按命中率降序
                // 2. 相同命中率时按出手次数降序（体现稳定性）
                // 3. 相同出手次数时按命中数降序（体现绝对能力）
                // 4. 最后按名称升序（保证排序一致性）
                .sorted(Comparator
                        .comparing((MatchGameStatsDTO.RankItem i) -> i.getRate() != null ? i.getRate() : 0D, Comparator.naturalOrder())
                        .reversed()
                        .thenComparing(i -> i.getAttempt() != null ? i.getAttempt() : 0L, Comparator.reverseOrder())
                        .thenComparing(i -> i.getMade() != null ? i.getMade() : 0L, Comparator.reverseOrder())
                        .thenComparing(MatchGameStatsDTO.RankItem::getName))
                .collect(Collectors.toList());

        return new MatchGameStatsDTO.Leaderboard(metric, metric.getDesc() + "榜", items);
    }

    /**
     * 数据聚合器
     * <p>用于在数据处理过程中累加各项统计指标
     * <p>所有字段初始化为0，通过累加操作逐步构建完整的统计数据
     */
    private static class Accumulator {
        /** 参与场次统计 */
        long appearances;

        /** 基础技术统计 */
        long score;    // 得分
        long rebound;  // 篮板
        long assist;   // 助攻
        long steal;    // 抢断
        long block;    // 盖帽

        /** 投篮统计 */
        long fgAttempt; // 投篮出手次数
        long fgMade;    // 投篮命中次数

        /** 三分球统计 */
        long threeAttempt; // 三分出手次数
        long threeMade;    // 三分命中次数

        /** 荣誉统计 */
        long mvp;  // MVP次数
        long svp;  // SVP次数

        /** 失误统计 */
        long turnover; // 失误次数
    }
}
