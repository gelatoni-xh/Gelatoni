package com.csxuhuan.gelatoni.interfaces.web.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 比赛分页查询请求 DTO
 *
 * <p>用于接收前端分页查询比赛列表的请求参数，支持按赛季筛选。
 *
 * @author Gelatoni
 */
public class MatchGamePageRequest {

    /** 页码，从 1 开始 */
    @NotNull(message = "页码不能为空")
    @Min(value = 1, message = "页码最小值为 1")
    private Integer pageNum;

    /** 每页大小 */
    @NotNull(message = "每页大小不能为空")
    @Min(value = 1, message = "每页大小最小值为 1")
    private Integer pageSize;

    /** 赛季标识，例如 S1、2026Q1，可选 */
    private String season;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }
}