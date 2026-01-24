package com.csxuhuan.gelatoni.domain.service.impl;

import com.csxuhuan.gelatoni.domain.model.entity.TodoTag;
import com.csxuhuan.gelatoni.domain.query.TodoTagCreateQuery;
import com.csxuhuan.gelatoni.domain.service.TodoTagDomainService;
import com.csxuhuan.gelatoni.infrastructure.repository.TodoTagRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 标签领域服务实现
 */
@Service
public class TodoTagDomainServiceImpl implements TodoTagDomainService {

    private final TodoTagRepository todoTagRepository;

    public TodoTagDomainServiceImpl(TodoTagRepository todoTagRepository) {
        this.todoTagRepository = todoTagRepository;
    }

    @Override
    public List<TodoTag> findAll() {
        return todoTagRepository.findAll();
    }

    @Override
    public int create(TodoTagCreateQuery query) {
        TodoTag tag = query.toTodoTag();
        return todoTagRepository.create(tag);
    }
}
