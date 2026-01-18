package com.csxuhuan.gelatoni.domain.query;


/**
 * 公告分页查询条件（领域对象）
 *
 * <p>
 * 表达「以某种业务规则分页查询公告」这一领域意图。
 * </p>
 */
public class NoticePageQuery extends PageQuery {

    /**
     * 是否包含已删除公告
     */
    private final boolean includeDeleted;

    /**
     * 构造一个分页查询条件
     * @param pageNo 页码
     * @param pageSize 每页大小
     * @param includeDeleted 是否包含已删除公告
     */
    public NoticePageQuery(int pageNo, int pageSize, boolean includeDeleted) {
        super(pageNo, pageSize);
        this.includeDeleted = includeDeleted ;
    }

    /**
     * 创建一个「默认」的分页查询条件
     * @param pageNo 页码
     * @param pageSize 每页大小
     */
    public NoticePageQuery(int pageNo, int pageSize) {
        this(pageNo, pageSize, false);
    }

    public boolean isIncludeDeleted() {
        return includeDeleted;
    }
}
