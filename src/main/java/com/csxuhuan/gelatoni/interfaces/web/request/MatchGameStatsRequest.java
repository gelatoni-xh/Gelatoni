package com.csxuhuan.gelatoni.interfaces.web.request;

import javax.validation.constraints.NotNull;

/**
 * 比赛数据统计请求 DTO
 *
 * <p>用于比赛数据分析页面的统计查询。
 * 支持两个筛选维度：
 * <ul>
 *     <li>全赛季 / 指定赛季（season 为空表示全赛季）</li>
 *     <li>统计维度：按用户维度、按球员维度</li>
 * </ul>
 *
 * <p>设计约束：
 * 只统计我方数据（team_type = 1）。
 */
public class MatchGameStatsRequest {

    /** 赛季（可选）。为空/空字符串表示全赛季 */
    private String season;

    /** 统计维度（必填）：PLAYER=球员维度，USER=用户维度 */
    @NotNull(message = "统计维度不能为空")
    private StatsDimension dimension;

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public StatsDimension getDimension() {
        return dimension;
    }

    public void setDimension(StatsDimension dimension) {
        this.dimension = dimension;
    }

    /**
     * 统计维度
     */
    public enum StatsDimension {
        /** 球员维度 */
        PLAYER,
        /** 用户维度 */
        USER
    }
}
