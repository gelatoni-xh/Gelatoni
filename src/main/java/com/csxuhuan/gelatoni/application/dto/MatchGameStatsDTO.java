package com.csxuhuan.gelatoni.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * 比赛数据统计返回 DTO
 *
 * <p>说明：
 * <ul>
 *     <li>只统计我方数据（team_type=1）</li>
 *     <li>season 为空表示全赛季</li>
 *     <li>命中率类榜单：使用 rate 表示（范围 0~1），同时返回 made/attempt 便于前端展示</li>
 * </ul>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MatchGameStatsDTO {

    /** 赛季（为空表示全赛季） */
    private String season;

    /** 统计维度 */
    private Dimension dimension;

    /** 各榜单结果 */
    private List<Leaderboard> leaderboards;

    // Jackson反序列化需要无参构造函数
    public MatchGameStatsDTO() {
    }

    public MatchGameStatsDTO(String season, Dimension dimension, List<Leaderboard> leaderboards) {
        this.season = season;
        this.dimension = dimension;
        this.leaderboards = leaderboards;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public Dimension getDimension() {
        return dimension;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    public List<Leaderboard> getLeaderboards() {
        return leaderboards;
    }

    public void setLeaderboards(List<Leaderboard> leaderboards) {
        this.leaderboards = leaderboards;
    }

    /**
     * 统计维度
     */
    public enum Dimension {
        /** 球员维度 */
        PLAYER,
        /** 用户维度 */
        USER
    }

    /**
     * 榜单类型枚举
     */
    public enum Metric {
        /** 上场次数（仅球员维度） */
        APPEARANCES,
        SCORE,
        REBOUND,
        ASSIST,
        STEAL,
        BLOCK,
        FG_ATTEMPT,
        FG_MADE,
        FG_PCT,
        THREE_ATTEMPT,
        THREE_MADE,
        THREE_PCT,
        MVP,
        SVP,
        TURNOVER
    }

    /**
     * 单个榜单
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Leaderboard {
        private Metric metric;
        private List<RankItem> items;

        // Jackson反序列化需要无参构造函数
        public Leaderboard() {
        }

        public Leaderboard(Metric metric, List<RankItem> items) {
            this.metric = metric;
            this.items = items;
        }

        public Metric getMetric() {
            return metric;
        }

        public void setMetric(Metric metric) {
            this.metric = metric;
        }

        public List<RankItem> getItems() {
            return items;
        }

        public void setItems(List<RankItem> items) {
            this.items = items;
        }
    }

    /**
     * 榜单条目
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RankItem {
        /** 名称：球员名/用户名 */
        private String name;

        /** 数值类榜单：得分/篮板/助攻等，使用 value 返回 */
        private Long value;

        /** 命中率榜单：命中数 */
        private Long made;

        /** 命中率榜单：出手数 */
        private Long attempt;

        /** 命中率榜单：命中率（0~1） */
        private Double rate;

        // Jackson反序列化需要无参构造函数
        public RankItem() {
        }

        public RankItem(String name, Long value) {
            this.name = name;
            this.value = value;
        }

        public RankItem(String name, Long made, Long attempt, Double rate) {
            this.name = name;
            this.made = made;
            this.attempt = attempt;
            this.rate = rate;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Long getValue() {
            return value;
        }

        public void setValue(Long value) {
            this.value = value;
        }

        public Long getMade() {
            return made;
        }

        public void setMade(Long made) {
            this.made = made;
        }

        public Long getAttempt() {
            return attempt;
        }

        public void setAttempt(Long attempt) {
            this.attempt = attempt;
        }

        public Double getRate() {
            return rate;
        }

        public void setRate(Double rate) {
            this.rate = rate;
        }
    }
}
