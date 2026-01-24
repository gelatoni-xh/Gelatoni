package com.csxuhuan.gelatoni.application.service.impl;

import com.csxuhuan.gelatoni.application.service.TodoItemAppService;
import com.csxuhuan.gelatoni.domain.model.entity.TodoItem;
import com.csxuhuan.gelatoni.domain.query.TodoItemCreateQuery;
import com.csxuhuan.gelatoni.domain.query.TodoItemUpdateQuery;
import com.csxuhuan.gelatoni.domain.service.TodoItemDomainService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TODO项应用服务实现类
 *
 * <p>
 * 提供TODO项相关的业务操作实现
 * </p>
 */
@Service
public class TodoItemAppServiceImpl implements TodoItemAppService {

    private final TodoItemDomainService todoItemDomainService;

    public TodoItemAppServiceImpl(TodoItemDomainService todoItemDomainService) {
        this.todoItemDomainService = todoItemDomainService;
    }

    @Override
    public List<TodoItem> findAll() {
        return todoItemDomainService.findAll();
    }

    @Override
    public List<TodoItem> findByTagId(Long tagId) {
        return todoItemDomainService.findByTagId(tagId);
    }

    @Override
    public int create(TodoItemCreateQuery query) {
        return todoItemDomainService.create(query);
    }

    @Override
    public int update(TodoItemUpdateQuery query) {
        return todoItemDomainService.update(query);
    }
}
