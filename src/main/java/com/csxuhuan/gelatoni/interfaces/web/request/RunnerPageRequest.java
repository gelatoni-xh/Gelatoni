package com.csxuhuan.gelatoni.interfaces.web.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Runner 分页查询请求
 */
public class RunnerPageRequest {

    @NotNull(message = "页码不能为空")
    @Min(value = 1, message = "页码最小值为 1")
    private Integer pageNum;

    @NotNull(message = "每页大小不能为空")
    @Min(value = 1, message = "每页大小最小值为 1")
    private Integer pageSize;

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
}
