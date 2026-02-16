package com.csxuhuan.gelatoni.application.dto;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 比赛统计指标枚举
 */
public enum MatchGameStatsMetric {
    APPEARANCES("上场次数", true, false),
    RATING_AVG("场均评价", true, true),
    SCORE("得分", false, false),
    SCORE_AVG("场均得分", true, true),
    REBOUND("篮板", false, false),
    REBOUND_AVG("场均篮板", true, true),
    ASSIST("助攻", false, false),
    ASSIST_AVG("场均助攻", true, true),
    STEAL("抢断", false, false),
    STEAL_AVG("场均抢断", true, true),
    BLOCK("盖帽", false, false),
    BLOCK_AVG("场均盖帽", true, true),
    FG_ATTEMPT("投篮出手", false, false),
    FG_ATTEMPT_AVG("场均投篮出手", true, true),
    FG_MADE("投篮命中", false, false),
    FG_MADE_AVG("场均投篮命中", true, true),
    FG_PCT("投篮命中率", false, false),
    FG_PCT_AVG("场均投篮命中率", true, true),
    THREE_ATTEMPT("三分出手", false, false),
    THREE_ATTEMPT_AVG("场均三分出手", true, true),
    THREE_MADE("三分命中", false, false),
    THREE_MADE_AVG("场均三分命中", true, true),
    THREE_PCT("三分命中率", false, false),
    THREE_PCT_AVG("场均三分命中率", true, true),
    MVP("MVP次数", true, true),
    MVP_AVG("场均MVP", false, false),
    SVP("SVP次数", true, true),
    SVP_AVG("场均SVP", false, false),
    TURNOVER("失误", false, false),
    TURNOVER_AVG("场均失误", true, true);

    private final String desc;
    private final boolean defaultVisibleForPlayer;
    private final boolean defaultVisibleForUser;

    MatchGameStatsMetric(String desc, boolean defaultVisibleForPlayer, boolean defaultVisibleForUser) {
        this.desc = desc;
        this.defaultVisibleForPlayer = defaultVisibleForPlayer;
        this.defaultVisibleForUser = defaultVisibleForUser;
    }

    public String getDesc() {
        return desc;
    }

    public boolean isDefaultVisibleForPlayer() {
        return defaultVisibleForPlayer;
    }

    public boolean isDefaultVisibleForUser() {
        return defaultVisibleForUser;
    }

    public static List<MetricConfig> getAllMetricConfigs() {
        return Arrays.stream(values())
                .map(m -> new MetricConfig(m.name(), m.desc, m.defaultVisibleForPlayer, m.defaultVisibleForUser))
                .collect(Collectors.toList());
    }

    public static class MetricConfig {
        private String metric;
        private String desc;
        private boolean defaultVisibleForPlayer;
        private boolean defaultVisibleForUser;

        public MetricConfig(String metric, String desc, boolean defaultVisibleForPlayer, boolean defaultVisibleForUser) {
            this.metric = metric;
            this.desc = desc;
            this.defaultVisibleForPlayer = defaultVisibleForPlayer;
            this.defaultVisibleForUser = defaultVisibleForUser;
        }

        public String getMetric() {
            return metric;
        }

        public void setMetric(String metric) {
            this.metric = metric;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public boolean isDefaultVisibleForPlayer() {
            return defaultVisibleForPlayer;
        }

        public void setDefaultVisibleForPlayer(boolean defaultVisibleForPlayer) {
            this.defaultVisibleForPlayer = defaultVisibleForPlayer;
        }

        public boolean isDefaultVisibleForUser() {
            return defaultVisibleForUser;
        }

        public void setDefaultVisibleForUser(boolean defaultVisibleForUser) {
            this.defaultVisibleForUser = defaultVisibleForUser;
        }
    }
}
