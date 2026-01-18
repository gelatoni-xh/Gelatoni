package com.csxuhuan.gelatoni.domain.result;

import com.csxuhuan.gelatoni.domain.model.entity.Notice;

import java.util.List;

/**
 * 领域层分页结果
 *
 * @param <T> 领域实体类型
 */
public class PageResult<T> {

    /**
     * 当前页数据
     */
    private final List<T> records;

    /**
     * 当前页码
     */
    private final int pageNo;

    /**
     * 每页大小
     */
    private final int pageSize;

    /**
     * 总记录数
     */
    private final long total;

    public PageResult(List<T> records, int pageNo, int pageSize, long total) {
        this.records = records;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.total = total;
    }

    /**
     * 创建领域层分页结果
     *
     * @param records 当前页数据
     * @param current 当前页码
     * @param size 每页大小
     * @param total 总记录数
     * @return 领域层分页结果
     */
    public static PageResult<Notice> of(List<Notice> records, long current, long size, long total) {
        return new PageResult<>(records, (int) current, (int) size, total);
    }

    public List<T> getRecords() {
        return records;
    }

    public int getPageNo() {
        return pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public long getTotal() {
        return total;
    }
}
