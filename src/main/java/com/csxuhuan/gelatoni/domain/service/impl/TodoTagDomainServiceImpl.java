package com.csxuhuan.gelatoni.domain.service.impl;

import com.csxuhuan.gelatoni.domain.model.entity.TodoTag;
import com.csxuhuan.gelatoni.domain.query.TodoTagCreateQuery;
import com.csxuhuan.gelatoni.domain.service.TodoTagDomainService;
import com.csxuhuan.gelatoni.infrastructure.repository.TodoTagRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TODO 标签领域服务实现
 *
 * <p>实现 {@link TodoTagDomainService} 接口，处理标签相关的领域逻辑。
 *
 * @author csxuhuan
 */
@Service
public class TodoTagDomainServiceImpl implements TodoTagDomainService {

    private final TodoTagRepository todoTagRepository;

    /**
     * 构造函数，注入仓储接口
     *
     * @param todoTagRepository 标签仓储
     */
    public TodoTagDomainServiceImpl(TodoTagRepository todoTagRepository) {
        this.todoTagRepository = todoTagRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TodoTag> findAll() {
        return todoTagRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int create(TodoTagCreateQuery query) {
        TodoTag tag = query.toTodoTag();
        return todoTagRepository.create(tag);
    }
}
