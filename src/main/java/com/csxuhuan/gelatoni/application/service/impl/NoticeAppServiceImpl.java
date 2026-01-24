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
 * <p>实现 {@link NoticeAppService} 接口，委托给领域服务处理具体业务逻辑。
 * 当前实现较为简单，直接透传给领域服务。
 *
 * <p>扩展点：
 * <ul>
 *     <li>添加事务管理（@Transactional）</li>
 *     <li>添加缓存处理</li>
 *     <li>添加消息发送（如公告发布通知）</li>
 *     <li>组合多个领域服务的调用</li>
 * </ul>
 *
 * @author csxuhuan
 */
@Service
public class NoticeAppServiceImpl implements NoticeAppService {

    private final NoticeDomainService noticeDomainService;

    /**
     * 构造函数，注入领域服务
     *
     * @param noticeDomainService 公告领域服务
     */
    public NoticeAppServiceImpl(NoticeDomainService noticeDomainService) {
        this.noticeDomainService = noticeDomainService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PageResult<Notice> pageQuery(NoticePageQuery query) {
        return noticeDomainService.pageQuery(query);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int create(NoticeCreateQuery query) {
        return noticeDomainService.create(query);
    }
}
