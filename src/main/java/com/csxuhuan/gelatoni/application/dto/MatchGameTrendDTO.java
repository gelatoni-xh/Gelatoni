package com.csxuhuan.gelatoni.application.dto;

import java.util.List;
import java.util.Map;

/**
 * 比赛趋势统计返回 DTO
 *
 * <p>按日期分组统计比赛数据，用于展示趋势图表。
 */
public class MatchGameTrendDTO {

    /** 日期列表 */
    private List<String> dates;

    /** 胜率趋势数据 */
    private List<Double> winRate;

    /** 各球员的各指标趋势数据：{playerName: {metric: [values]}} */
    private Map<String, Map<String, List<Double>>> playerMetrics;

    public MatchGameTrendDTO() {
    }

    public MatchGameTrendDTO(List<String> dates, List<Double> winRate, Map<String, Map<String, List<Double>>> playerMetrics) {
        this.dates = dates;
        this.winRate = winRate;
        this.playerMetrics = playerMetrics;
    }

    public List<String> getDates() {
        return dates;
    }

    public void setDates(List<String> dates) {
        this.dates = dates;
    }

    public List<Double> getWinRate() {
        return winRate;
    }

    public void setWinRate(List<Double> winRate) {
        this.winRate = winRate;
    }

    public Map<String, Map<String, List<Double>>> getPlayerMetrics() {
        return playerMetrics;
    }

    public void setPlayerMetrics(Map<String, Map<String, List<Double>>> playerMetrics) {
        this.playerMetrics = playerMetrics;
    }
}
