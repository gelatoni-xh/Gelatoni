package com.csxuhuan.gelatoni.domain.query;

/**
 * 比赛分页查询对象
 *
 * <p>用于接收分页查询比赛列表的参数，支持按赛季筛选。
 *
 * @author Gelatoni
 */
public class MatchGamePageQuery extends PageQuery {

    /** 赛季标识，例如 S1、2026Q1 */
    private String season;

    public MatchGamePageQuery(Integer pageNum, Integer pageSize, String season) {
        super(pageNum, pageSize);
        this.season = season;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }
}