package com.csxuhuan.gelatoni.domain.query;


/**
 * 领域层的分页查询抽象
 *
 * <p>
 * 表达的是「业务上一次分页查询的意图」，而不是技术分页实现。
 * 不包含任何 MyBatis / Web / SQL 相关概念。
 * </p>
 */
public abstract class PageQuery {

    /**
     * 页码（从 1 开始）
     */
    private final int pageNo;

    /**
     * 每页大小
     */
    private final int pageSize;

    protected PageQuery(int pageNo, int pageSize) {
        if (pageNo <= 0) {
            throw new IllegalArgumentException("pageNo must be greater than 0");
        }
        if (pageSize <= 0) {
            throw new IllegalArgumentException("pageSize must be greater than 0");
        }
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }
}