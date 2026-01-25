package com.csxuhuan.gelatoni.application.service.impl;

import com.csxuhuan.gelatoni.application.service.TodoAppService;
import com.csxuhuan.gelatoni.domain.model.entity.TodoItem;
import com.csxuhuan.gelatoni.domain.model.entity.TodoTag;
import com.csxuhuan.gelatoni.domain.query.TodoItemCreateQuery;
import com.csxuhuan.gelatoni.domain.query.TodoItemUpdateQuery;
import com.csxuhuan.gelatoni.domain.query.TodoTagCreateQuery;
import com.csxuhuan.gelatoni.domain.service.TodoItemDomainService;
import com.csxuhuan.gelatoni.domain.service.TodoTagDomainService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TODO 应用服务实现类
 *
 * <p>实现 {@link TodoAppService} 接口，委托给领域服务处理具体业务逻辑。
 *
 * <p>扩展点：
 * <ul>
 *     <li>添加事务管理（@Transactional）</li>
 *     <li>添加权限校验</li>
 *     <li>添加操作日志记录</li>
 *     <li>添加标签名称唯一性校验</li>
 *     <li>添加标签删除时的级联处理</li>
 * </ul>
 *
 * @author csxuhuan
 */
@Service
public class TodoAppServiceImpl implements TodoAppService {

    private final TodoItemDomainService todoItemDomainService;
    private final TodoTagDomainService todoTagDomainService;

    /**
     * 构造函数，注入领域服务
     *
     * @param todoItemDomainService TODO 项领域服务
     * @param todoTagDomainService  标签领域服务
     */
    public TodoAppServiceImpl(TodoItemDomainService todoItemDomainService, TodoTagDomainService todoTagDomainService) {
        this.todoItemDomainService = todoItemDomainService;
        this.todoTagDomainService = todoTagDomainService;
    }

    // ========== TODO 项相关方法 ==========

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TodoItem> findAllItems(Long userId) {
        return todoItemDomainService.findAll(userId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TodoItem> findItemsByTagId(Long tagId, Long userId) {
        return todoItemDomainService.findByTagId(tagId, userId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int createItem(TodoItemCreateQuery query, Long userId) {
        return todoItemDomainService.create(query, userId, userId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int updateItem(TodoItemUpdateQuery query, Long userId) {
        return todoItemDomainService.update(query, userId, userId);
    }

    // ========== TODO 标签相关方法 ==========

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TodoTag> findAllTags(Long userId) {
        return todoTagDomainService.findAll(userId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int createTag(TodoTagCreateQuery query, Long userId) {
        return todoTagDomainService.create(query, userId, userId);
    }
}
