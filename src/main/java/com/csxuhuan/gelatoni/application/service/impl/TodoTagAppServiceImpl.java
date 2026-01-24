package com.csxuhuan.gelatoni.application.service.impl;

import com.csxuhuan.gelatoni.application.service.TodoTagAppService;
import com.csxuhuan.gelatoni.domain.model.entity.TodoTag;
import com.csxuhuan.gelatoni.domain.query.TodoTagCreateQuery;
import com.csxuhuan.gelatoni.domain.service.TodoTagDomainService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 标签应用服务实现类
 *
 * <p>
 * 提供标签相关的业务操作实现
 * </p>
 */
@Service
public class TodoTagAppServiceImpl implements TodoTagAppService {

    private final TodoTagDomainService todoTagDomainService;

    public TodoTagAppServiceImpl(TodoTagDomainService todoTagDomainService) {
        this.todoTagDomainService = todoTagDomainService;
    }

    @Override
    public List<TodoTag> findAll() {
        return todoTagDomainService.findAll();
    }

    @Override
    public int create(TodoTagCreateQuery query) {
        return todoTagDomainService.create(query);
    }
}
