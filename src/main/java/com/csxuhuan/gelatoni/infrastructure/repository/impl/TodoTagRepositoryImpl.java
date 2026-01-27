package com.csxuhuan.gelatoni.infrastructure.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.csxuhuan.gelatoni.domain.model.converter.TodoTagConverter;
import com.csxuhuan.gelatoni.domain.model.entity.TodoTag;
import com.csxuhuan.gelatoni.infrastructure.repository.TodoTagRepository;
import com.csxuhuan.gelatoni.infrastructure.repository.entity.TodoTagDO;
import com.csxuhuan.gelatoni.infrastructure.repository.mapper.TodoTagMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
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
    public List<TodoTag> findAll(Long userId) {
        LambdaQueryWrapper<TodoTagDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TodoTagDO::getIsDeleted, false)
                .eq(TodoTagDO::getUserId, userId)
                .orderByDesc(TodoTagDO::getCreateTime);

        List<TodoTagDO> tagDOList = todoTagMapper.selectList(wrapper);

        return tagDOList.stream()
                .map(TodoTagConverter::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public TodoTag findById(Long id, Long userId) {
        LambdaQueryWrapper<TodoTagDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TodoTagDO::getId, id)
                .eq(TodoTagDO::getIsDeleted, false)
                .eq(TodoTagDO::getUserId, userId);

        TodoTagDO tagDO = todoTagMapper.selectOne(wrapper);
        return TodoTagConverter.toDomain(tagDO);
    }

    @Override
    public int create(TodoTag tag, Long userId, Long creator) {
        TodoTagDO tagDO = TodoTagConverter.toDO(tag);
        tagDO.setUserId(userId);
        tagDO.setCreator(creator);
        tagDO.setModifier(creator);
        return todoTagMapper.insert(tagDO);
    }

    @Override
    public int delete(Long id, Long userId, Long modifier) {
        LambdaUpdateWrapper<TodoTagDO> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(TodoTagDO::getId, id)
                .eq(TodoTagDO::getIsDeleted, false)
                .eq(TodoTagDO::getUserId, userId);

        TodoTagDO tagDO = new TodoTagDO();
        tagDO.setIsDeleted(true); // 软删除标记
        tagDO.setModifier(modifier);
        tagDO.setModifiedTime(LocalDateTime.now());

        return todoTagMapper.update(tagDO, wrapper);
    }
}
