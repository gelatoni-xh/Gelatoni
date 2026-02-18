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

    /** 各指标的趋势数据 */
    private Map<String, List<Double>> metrics;

    public MatchGameTrendDTO() {
    }

    public MatchGameTrendDTO(List<String> dates, Map<String, List<Double>> metrics) {
        this.dates = dates;
        this.metrics = metrics;
    }

    public List<String> getDates() {
        return dates;
    }

    public void setDates(List<String> dates) {
        this.dates = dates;
    }

    public Map<String, List<Double>> getMetrics() {
        return metrics;
    }

    public void setMetrics(Map<String, List<Double>> metrics) {
        this.metrics = metrics;
    }
}
