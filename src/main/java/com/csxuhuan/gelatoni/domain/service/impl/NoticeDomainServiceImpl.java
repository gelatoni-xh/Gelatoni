package com.csxuhuan.gelatoni.domain.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.csxuhuan.gelatoni.domain.model.entity.Notice;
import com.csxuhuan.gelatoni.domain.query.NoticePageQuery;
import com.csxuhuan.gelatoni.domain.result.PageResult;
import com.csxuhuan.gelatoni.domain.service.NoticeDomainService;
import com.csxuhuan.gelatoni.infrastructure.repository.NoticeRepository;
import org.springframework.stereotype.Service;


/**
 * 公告领域服务实现
 */
@Service
public class NoticeDomainServiceImpl implements NoticeDomainService {

    private final NoticeRepository noticeRepository;

    public NoticeDomainServiceImpl(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    /**
     * 分页查询
     * @param query 查询条件
     * @return 分页结果
     */
    @Override
    public PageResult<Notice> pageQuery(NoticePageQuery query) {
        IPage<Notice> page = noticeRepository.pageNotices(query.getPageNo(), query.getPageSize());
        return PageResult.of(page.getRecords(), page.getCurrent(), page.getSize(), page.getTotal());
    }
}