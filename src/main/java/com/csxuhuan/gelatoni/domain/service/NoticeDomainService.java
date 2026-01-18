package com.csxuhuan.gelatoni.domain.service;

import com.csxuhuan.gelatoni.domain.model.entity.Notice;
import com.csxuhuan.gelatoni.domain.query.NoticePageQuery;
import com.csxuhuan.gelatoni.domain.result.PageResult;

/**
 * 公告领域服务
 *
 * 说明：
 * - 负责公告相关的领域行为
 * - 分页查询是领域内的“集合获取规则”
 */
public interface NoticeDomainService {

    /**
     * 分页查询公告
     *
     * @param query 查询条件
     * @return 分页结果
     */
    PageResult<Notice> pageQuery(NoticePageQuery query);
}