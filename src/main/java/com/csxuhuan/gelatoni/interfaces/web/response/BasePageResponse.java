package com.csxuhuan.gelatoni.interfaces.web.response;


import java.util.List;

/**
 * 分页响应的协议基类
 *
 * @param <T> 前端展示用 DTO
 */
public class BasePageResponse<T> {

    /**
     * 当前页数据列表
     */
    private List<T> list;

    /**
     * 总记录数
     */
    private Long total;

    /**
     * 当前页码
     */
    private Integer pageNo;

    /**
     * 每页大小
     */
    private Integer pageSize;

    public BasePageResponse(List<T> collect, long total, int pageNo, int pageSize) {
        this.list = collect;
        this.total = total;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

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
