package com.csxuhuan.gelatoni.infrastructure.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.csxuhuan.gelatoni.domain.model.converter.TodoTagConverter;
import com.csxuhuan.gelatoni.domain.model.entity.TodoTag;
import com.csxuhuan.gelatoni.infrastructure.repository.TodoTagRepository;
import com.csxuhuan.gelatoni.infrastructure.repository.entity.TodoTagDO;
import com.csxuhuan.gelatoni.infrastructure.repository.mapper.TodoTagMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * TODO 标签仓储实现
 *
 * <p>实现 {@link TodoTagRepository} 接口，使用 MyBatis-Plus 进行数据访问。
 *
 * @author csxuhuan
 */
@Repository
public class TodoTagRepositoryImpl implements TodoTagRepository {

    private final TodoTagMapper todoTagMapper;

    /**
     * 构造函数，注入 Mapper
     *
     * @param todoTagMapper 标签 Mapper
     */
    public TodoTagRepositoryImpl(TodoTagMapper todoTagMapper) {
        this.todoTagMapper = todoTagMapper;
    }

    @Override
    public List<TodoTag> findAll() {
        LambdaQueryWrapper<TodoTagDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TodoTagDO::getIsDeleted, false)
                .orderByDesc(TodoTagDO::getCreateTime);

        List<TodoTagDO> tagDOList = todoTagMapper.selectList(wrapper);

        return tagDOList.stream()
                .map(TodoTagConverter::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public TodoTag findById(Long id) {
        LambdaQueryWrapper<TodoTagDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TodoTagDO::getId, id)
                .eq(TodoTagDO::getIsDeleted, false);

        TodoTagDO tagDO = todoTagMapper.selectOne(wrapper);
        return TodoTagConverter.toDomain(tagDO);
    }

    @Override
    public int create(TodoTag tag) {
        return todoTagMapper.insert(TodoTagConverter.toDO(tag));
    }
}
