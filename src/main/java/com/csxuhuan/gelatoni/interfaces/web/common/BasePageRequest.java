package com.csxuhuan.gelatoni.interfaces.web.common;

import javax.validation.constraints.Min;

/**
 * 分页请求的协议基类
 *
 * 语义：
 * - 这是「前端请求分页」的统一协议
 * - 不等同于 MyBatis / Domain 分页
 */
public abstract class BasePageRequest {

    /**
     * 页码，从 1 开始
     */
    @Min(value = 1, message = "页码不能小于 1")
    private Integer pageNo = 1;

    /**
     * 每页大小
     */
    @Min(value = 1, message = "每页大小不能小于 1")
    private Integer pageSize = 10;

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
