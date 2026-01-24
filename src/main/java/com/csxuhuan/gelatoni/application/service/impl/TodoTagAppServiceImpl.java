package com.csxuhuan.gelatoni.application.service.impl;

import com.csxuhuan.gelatoni.application.service.TodoTagAppService;
import com.csxuhuan.gelatoni.domain.model.entity.TodoTag;
import com.csxuhuan.gelatoni.domain.query.TodoTagCreateQuery;
import com.csxuhuan.gelatoni.domain.service.TodoTagDomainService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TODO 标签应用服务实现类
 *
 * <p>实现 {@link TodoTagAppService} 接口，委托给领域服务处理具体业务逻辑。
 *
 * <p>扩展点：
 * <ul>
 *     <li>添加标签名称唯一性校验</li>
 *     <li>添加标签删除时的级联处理</li>
 * </ul>
 *
 * @author csxuhuan
 */
@Service
public class TodoTagAppServiceImpl implements TodoTagAppService {

    private final TodoTagDomainService todoTagDomainService;

    /**
     * 构造函数，注入领域服务
     *
     * @param todoTagDomainService 标签领域服务
     */
    public TodoTagAppServiceImpl(TodoTagDomainService todoTagDomainService) {
        this.todoTagDomainService = todoTagDomainService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TodoTag> findAll() {
        return todoTagDomainService.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int create(TodoTagCreateQuery query) {
        return todoTagDomainService.create(query);
    }
}
