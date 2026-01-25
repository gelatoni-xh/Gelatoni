package com.csxuhuan.gelatoni.domain.service.impl;

import com.csxuhuan.gelatoni.application.exception.BizErrorCode;
import com.csxuhuan.gelatoni.application.exception.BizException;
import com.csxuhuan.gelatoni.domain.model.entity.TodoItem;
import com.csxuhuan.gelatoni.domain.query.TodoItemCreateQuery;
import com.csxuhuan.gelatoni.domain.query.TodoItemUpdateQuery;
import com.csxuhuan.gelatoni.domain.service.TodoItemDomainService;
import com.csxuhuan.gelatoni.infrastructure.repository.TodoItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TODO 项领域服务实现
 *
 * <p>实现 {@link TodoItemDomainService} 接口，处理 TODO 项相关的领域逻辑。
 *
 * @author csxuhuan
 */
@Service
public class TodoItemDomainServiceImpl implements TodoItemDomainService {

    private final TodoItemRepository todoItemRepository;

    /**
     * 构造函数，注入仓储接口
     *
     * @param todoItemRepository TODO 项仓储
     */
    public TodoItemDomainServiceImpl(TodoItemRepository todoItemRepository) {
        this.todoItemRepository = todoItemRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TodoItem> findAll(Long userId) {
        return todoItemRepository.findAll(userId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TodoItem> findByTagId(Long tagId, Long userId) {
        return todoItemRepository.findByTagId(tagId, userId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int create(TodoItemCreateQuery query, Long userId, Long creator) {
        TodoItem item = query.toTodoItem();
        return todoItemRepository.create(item, userId, creator);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int update(TodoItemUpdateQuery query, Long userId, Long modifier) {
        // 校验：TODO项存在且属于当前用户
        TodoItem existingItem = todoItemRepository.findById(query.getId(), userId);
        if (existingItem == null) {
            throw new BizException(BizErrorCode.OPERATION_NOT_ALLOWED, "TODO项不存在或无权访问");
        }

        TodoItem item = query.toTodoItem();
        return todoItemRepository.update(item, userId, modifier);
    }
}
