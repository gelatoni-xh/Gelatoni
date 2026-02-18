package com.csxuhuan.gelatoni.interfaces.web.request;

/**
 * 比赛趋势统计请求 DTO
 *
 * <p>用于按日期统计比赛数据趋势。
 * 支持：
 * <ul>
 *     <li>全赛季 / 指定赛季（season 为空表示全赛季）</li>
 *     <li>统计维度：按球员维度、按用户维度</li>
 * </ul>
 *
 * <p>设计约束：
 * <ul>
 *     <li>只统计我方数据（team_type=1）</li>
 *     <li>按日期分组统计</li>
 * </ul>
 */
public class MatchGameTrendRequest {

    /** 赛季（可选）。为空/空字符串表示全赛季 */
    private String season;

    /** 是否去除机器人 */
    private Boolean excludeRobot = true;

    /** 统计维度（必填）：PLAYER=球员维度，USER=用户维度 */
    private String dimension;

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public Boolean getExcludeRobot() {
        return excludeRobot;
    }

    public void setExcludeRobot(Boolean excludeRobot) {
        this.excludeRobot = excludeRobot;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }
}
