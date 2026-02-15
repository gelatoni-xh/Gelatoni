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
 *     <li>支持两种统计维度：PLAYER（球员维度）和 USER（用户维度）</li>
 *     <li>包含多个榜单类型，涵盖得分、篮板、助攻等核心统计数据</li>
 * </ul>
 *
 * <p>使用场景：
 * <ul>
 *     <li>球员个人数据排行榜展示</li>
 *     <li>用户参与度统计分析</li>
 *     <li>赛季数据汇总报告</li>
 * </ul>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MatchGameStatsDTO {

    /** 
     * 赛季标识
     * <p>为空表示统计全赛季数据，不为空则表示特定赛季的数据统计
     */
    private String season;

    /** 
     * 统计维度枚举
     * <p>PLAYER: 按球员维度统计<br>
     * USER: 按用户维度统计
     */
    private Dimension dimension;

    /** 
     * 各项统计数据榜单列表
     * <p>包含得分榜、篮板榜、助攻榜等各种统计维度的排名数据
     */
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
     * 统计维度枚举
     * <p>定义数据统计的不同维度视角
     */
    public enum Dimension {
        /** 
         * 球员维度统计
         * <p>以球员为主体进行数据统计和排名
         */
        PLAYER,
        /** 
         * 用户维度统计
         * <p>以系统用户为主体进行数据统计和排名
         */
        USER
    }

    /**
     * 榜单统计指标枚举
     * <p>定义各种统计数据指标类型，用于不同维度的排名统计
     */
    public enum Metric {
        /** 
         * 上场次数统计
         * <p>仅在PLAYER维度下有效，统计球员参赛场次
         */
        APPEARANCES("上场次数"),
        /** 得分统计 */
        SCORE("得分"),
        /**
         * 场均得分统计
         * <p>计算公式：总得分 / 上场次数，保留一位小数
         */
        SCORE_AVG("场均得分"),
        /** 篮板统计 */
        REBOUND("篮板"),
        /**
         * 场均篮板统计
         * <p>计算公式：总篮板 / 上场次数，保留一位小数
         */
        REBOUND_AVG("场均篮板"),
        /** 助攻统计 */
        ASSIST("助攻"),
        /**
         * 场均助攻统计
         * <p>计算公式：总助攻 / 上场次数，保留一位小数
         */
        ASSIST_AVG("场均助攻"),
        /** 抢断统计 */
        STEAL("抢断"),
        /**
         * 场均抢断统计
         * <p>计算公式：总抢断 / 上场次数，保留一位小数
         */
        STEAL_AVG("场均抢断"),
        /** 盖帽统计 */
        BLOCK("盖帽"),
        /**
         * 场均盖帽统计
         * <p>计算公式：总盖帽 / 上场次数，保留一位小数
         */
        BLOCK_AVG("场均盖帽"),
        /** 投篮出手次数 */
        FG_ATTEMPT("投篮出手"),
        /**
         * 场均投篮出手
         * <p>计算公式：总投篮出手 / 上场次数，保留一位小数
         */
        FG_ATTEMPT_AVG("场均投篮出手"),
        /** 投篮命中次数 */
        FG_MADE("投篮命中"),
        /**
         * 场均投篮命中
         * <p>计算公式：总投篮命中 / 上场次数，保留一位小数
         */
        FG_MADE_AVG("场均投篮命中"),
        /** 投篮命中率 */
        FG_PCT("投篮命中率"),
        /**
         * 场均投篮命中率
         * <p>返回场均出手、场均命中和整体命中率三个数据
         * <p>整体命中率计算公式：总命中 / 总出手
         * <p>场均出手计算公式：总出手 / 上场次数，保留一位小数
         * <p>场均命中计算公式：总命中 / 上场次数，保留一位小数
         */
        FG_PCT_AVG("场均投篮命中率"),
        /** 三分球出手次数 */
        THREE_ATTEMPT("三分出手"),
        /**
         * 场均三分出手
         * <p>计算公式：总三分出手 / 上场次数，保留一位小数
         */
        THREE_ATTEMPT_AVG("场均三分出手"),
        /** 三分球命中次数 */
        THREE_MADE("三分命中"),
        /**
         * 场均三分命中
         * <p>计算公式：总三分命中 / 上场次数，保留一位小数
         */
        THREE_MADE_AVG("场均三分命中"),
        /** 三分球命中率 */
        THREE_PCT("三分命中率"),
        /**
         * 场均三分命中率
         * <p>返回场均出手、场均命中和整体命中率三个数据
         * <p>整体命中率计算公式：总命中 / 总出手
         * <p>场均出手计算公式：总出手 / 上场次数，保留一位小数
         * <p>场均命中计算公式：总命中 / 上场次数，保留一位小数
         */
        THREE_PCT_AVG("场均三分命中率"),
        /** MVP次数统计 */
        MVP("MVP次数"),
        /** SVP次数统计 */
        SVP("SVP次数"),
        /** 失误统计 */
        TURNOVER("失误"),
        /**
         * 场均失误统计
         * <p>计算公式：总失误 / 上场次数，保留一位小数
         */
        TURNOVER_AVG("场均失误");
        
        private final String desc;
        
        Metric(String desc) {
            this.desc = desc;
        }
        
        public String getDesc() {
            return desc;
        }
    }

    /**
     * 单个榜单数据结构
     * <p>表示某一统计指标的完整排名榜单
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Leaderboard {
        /**
         * 榜单对应的统计指标
         * <p>如SCORE、REBOUND等具体统计维度
         */
        private Metric metric;
        /**
         * 榜单描述（带"榜"后缀）
         * <p>如"得分榜"、"篮板榜"等
         */
        private String metricDesc;
        /**
         * 排名条目列表
         * <p>按指定规则排序的具体排名数据
         */
        private List<RankItem> items;

        // Jackson反序列化需要无参构造函数
        public Leaderboard() {
        }

        public Leaderboard(Metric metric, String metricDesc, List<RankItem> items) {
            this.metric = metric;
            this.metricDesc = metricDesc;
            this.items = items;
        }

        public Metric getMetric() {
            return metric;
        }

        public void setMetric(Metric metric) {
            this.metric = metric;
        }

        public String getMetricDesc() {
            return metricDesc;
        }

        public void setMetricDesc(String metricDesc) {
            this.metricDesc = metricDesc;
        }

        public List<RankItem> getItems() {
            return items;
        }

        public void setItems(List<RankItem> items) {
            this.items = items;
        }
    }

    /**
     * 榜单排名条目
     * <p>表示榜单中的单个排名记录，包含排名对象的基本信息和统计数据
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RankItem {
        /**
         * 排名对象名称
         * <p>根据统计维度不同，可能是球员姓名或用户名
         */
        private String name;

        /**
         * 数值型统计数据
         * <p>用于得分、篮板、助攻等直接数值类榜单，存储具体的统计数值
         */
        private Long value;

        /**
         * 场均值
         * <p>用于场均类榜单，计算公式为：总数值 / 上场次数
         * <p>保留一位小数，例如：场均得分 = 总得分 / 上场次数
         */
        private Double avg;

        /**
         * 命中次数
         * <p>用于命中率类榜单，可以是总命中数或场均命中数
         * <p>总榜单：存储总命中次数（Long类型）
         * <p>场均榜单：存储场均命中数（Double类型，保留一位小数）
         */
        private Double made;

        /**
         * 出手次数
         * <p>用于命中率类榜单，可以是总出手数或场均出手数
         * <p>总榜单：存储总出手次数（Long类型）
         * <p>场均榜单：存储场均出手数（Double类型，保留一位小数）
         */
        private Double attempt;

        /**
         * 命中率
         * <p>专门用于命中率类榜单，计算公式为 made/attempt，取值范围0-1
         */
        private Double rate;

        // Jackson反序列化需要无参构造函数
        public RankItem() {
        }

        public RankItem(String name, Long value) {
            this.name = name;
            this.value = value;
        }

        public RankItem(String name, Double avg) {
            this.name = name;
            this.avg = avg;
        }

        public RankItem(String name, Double made, Double attempt, Double rate) {
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

        public Double getAvg() {
            return avg;
        }

        public void setAvg(Double avg) {
            this.avg = avg;
        }

        public Double getMade() {
            return made;
        }

        public void setMade(Double made) {
            this.made = made;
        }

        public Double getAttempt() {
            return attempt;
        }

        public void setAttempt(Double attempt) {
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
