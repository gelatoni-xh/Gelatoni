package com.csxuhuan.gelatoni.application.service;

import com.csxuhuan.gelatoni.domain.model.entity.Notice;
import com.csxuhuan.gelatoni.domain.query.NoticePageQuery;
import com.csxuhuan.gelatoni.domain.result.PageResult;

/**
 * 公告服务接口
 * 提供公告相关的业务操作
 */
public interface NoticeAppService {
    /**
     * 分页查询公告
     *
     * @param query 查询条件
     * @return 公告分页结果
     */
    PageResult<Notice> pageQuery(NoticePageQuery query);
}

