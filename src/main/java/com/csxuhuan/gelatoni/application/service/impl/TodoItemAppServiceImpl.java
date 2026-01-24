package com.csxuhuan.gelatoni.application.service.impl;

import com.csxuhuan.gelatoni.application.service.TodoItemAppService;
import com.csxuhuan.gelatoni.domain.model.entity.TodoItem;
import com.csxuhuan.gelatoni.domain.query.TodoItemCreateQuery;
import com.csxuhuan.gelatoni.domain.query.TodoItemUpdateQuery;
import com.csxuhuan.gelatoni.domain.service.TodoItemDomainService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TODO 项应用服务实现类
 *
 * <p>实现 {@link TodoItemAppService} 接口，委托给领域服务处理具体业务逻辑。
 *
 * <p>扩展点：
 * <ul>
 *     <li>添加事务管理（@Transactional）</li>
 *     <li>添加权限校验</li>
 *     <li>添加操作日志记录</li>
 * </ul>
 *
 * @author csxuhuan
 */
@Service
public class TodoItemAppServiceImpl implements TodoItemAppService {

    private final TodoItemDomainService todoItemDomainService;

    /**
     * 构造函数，注入领域服务
     *
     * @param todoItemDomainService TODO 项领域服务
     */
    public TodoItemAppServiceImpl(TodoItemDomainService todoItemDomainService) {
        this.todoItemDomainService = todoItemDomainService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TodoItem> findAll() {
        return todoItemDomainService.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TodoItem> findByTagId(Long tagId) {
        return todoItemDomainService.findByTagId(tagId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int create(TodoItemCreateQuery query) {
        return todoItemDomainService.create(query);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int update(TodoItemUpdateQuery query) {
        return todoItemDomainService.update(query);
    }
}
