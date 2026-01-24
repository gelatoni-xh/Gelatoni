package com.csxuhuan.gelatoni.domain.service.impl;

import com.csxuhuan.gelatoni.domain.model.entity.TodoItem;
import com.csxuhuan.gelatoni.domain.query.TodoItemCreateQuery;
import com.csxuhuan.gelatoni.domain.query.TodoItemUpdateQuery;
import com.csxuhuan.gelatoni.domain.service.TodoItemDomainService;
import com.csxuhuan.gelatoni.infrastructure.repository.TodoItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TODO项领域服务实现
 */
@Service
public class TodoItemDomainServiceImpl implements TodoItemDomainService {

    private final TodoItemRepository todoItemRepository;

    public TodoItemDomainServiceImpl(TodoItemRepository todoItemRepository) {
        this.todoItemRepository = todoItemRepository;
    }

    @Override
    public List<TodoItem> findAll() {
        return todoItemRepository.findAll();
    }

    @Override
    public List<TodoItem> findByTagId(Long tagId) {
        return todoItemRepository.findByTagId(tagId);
    }

    @Override
    public int create(TodoItemCreateQuery query) {
        TodoItem item = query.toTodoItem();
        return todoItemRepository.create(item);
    }

    @Override
    public int update(TodoItemUpdateQuery query) {
        TodoItem item = query.toTodoItem();
        return todoItemRepository.update(item);
    }
}
