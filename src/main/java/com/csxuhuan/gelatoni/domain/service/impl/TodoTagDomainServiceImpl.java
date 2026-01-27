package com.csxuhuan.gelatoni.domain.service.impl;

import com.csxuhuan.gelatoni.application.exception.BizErrorCode;
import com.csxuhuan.gelatoni.application.exception.BizException;
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
    public List<TodoTag> findAll(Long userId) {
        return todoTagRepository.findAll(userId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int create(TodoTagCreateQuery query, Long userId, Long creator) {
        TodoTag tag = query.toTodoTag();
        return todoTagRepository.create(tag, userId, creator);
    }

    @Override
    public int delete(Long id, Long userId, Long modifier) {
        // 校验：标签存在且属于当前用户
        TodoTag existingTag = todoTagRepository.findById(id, userId);
        if (existingTag == null) {
            throw new BizException(BizErrorCode.OPERATION_NOT_ALLOWED, "标签不存在或无权访问");
        }

        // 先将关联的TODO项的tagId设为null
        // 需要通过todoItemRepository来更新
        // 这里需要获取todoItemRepository，但由于架构限制，我们可能需要其他方式
        
        return todoTagRepository.delete(id, userId, modifier);
    }
}
