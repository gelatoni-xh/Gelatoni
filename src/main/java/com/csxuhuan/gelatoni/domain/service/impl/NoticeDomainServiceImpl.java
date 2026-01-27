package com.csxuhuan.gelatoni.domain.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.csxuhuan.gelatoni.domain.model.entity.Notice;
import com.csxuhuan.gelatoni.domain.query.NoticeCreateQuery;
import com.csxuhuan.gelatoni.domain.query.NoticePageQuery;
import com.csxuhuan.gelatoni.domain.result.PageResult;
import com.csxuhuan.gelatoni.domain.service.NoticeDomainService;
import com.csxuhuan.gelatoni.infrastructure.repository.NoticeRepository;
import org.springframework.stereotype.Service;

/**
 * 公告领域服务实现
 *
 * <p>实现 {@link NoticeDomainService} 接口，处理公告相关的领域逻辑。
 * 通过 {@link NoticeRepository} 访问数据，屏蔽持久化实现细节。
 *
 * @author csxuhuan
 */
@Service
public class NoticeDomainServiceImpl implements NoticeDomainService {

    private final NoticeRepository noticeRepository;

    /**
     * 构造函数，注入仓储接口
     *
     * @param noticeRepository 公告仓储
     */
    public NoticeDomainServiceImpl(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    /**
     * {@inheritDoc}
     *
     * <p>实现说明：委托给 Repository 执行分页查询，
     * 将 MyBatis-Plus 的 IPage 转换为领域层的 PageResult。
     */
    @Override
    public PageResult<Notice> pageQuery(NoticePageQuery query) {
        IPage<Notice> page = noticeRepository.pageNotices(query.getPageNo(), query.getPageSize());
        return PageResult.of(page.getRecords(), page.getCurrent(), page.getSize(), page.getTotal());
    }

    /**
     * {@inheritDoc}
     *
     * <p>实现说明：将查询对象转换为领域实体，然后委托给 Repository 持久化。
     */
    @Override
    public int create(NoticeCreateQuery query) {
        Notice notice = query.toNotice();
        return noticeRepository.createNotice(notice);
    }

    @Override
    public int delete(Long id, Long modifier) {
        return noticeRepository.deleteNotice(id, modifier);
    }
}