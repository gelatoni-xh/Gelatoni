package com.csxuhuan.gelatoni.application.service.impl;

import com.csxuhuan.gelatoni.application.service.NoticeAppService;
import com.csxuhuan.gelatoni.domain.model.entity.Notice;
import com.csxuhuan.gelatoni.domain.query.NoticeCreateQuery;
import com.csxuhuan.gelatoni.domain.query.NoticePageQuery;
import com.csxuhuan.gelatoni.domain.result.PageResult;
import com.csxuhuan.gelatoni.domain.service.NoticeDomainService;
import org.springframework.stereotype.Service;

/**
 * 公告应用服务实现类
 *
 * <p>
 * 提供公告相关的业务操作实现
 * </p>
 */
@Service
public class NoticeAppServiceImpl implements NoticeAppService {
    private final NoticeDomainService noticeDomainService;

    public NoticeAppServiceImpl(NoticeDomainService noticeDomainService) {
        this.noticeDomainService = noticeDomainService;
    }

    /**
     * 分页查询
     * @param query 查询条件
     * @return 分页结果
     */
    @Override
    public PageResult<Notice> pageQuery(NoticePageQuery query) {
        return noticeDomainService.pageQuery(query);
    }

    /**
     * 新增
     *
     * @param query 创建公告的参数
     * @return 新增的公告ID
     */
    @Override
    public int create(NoticeCreateQuery query) {
        return noticeDomainService.create(query);
    }
}
