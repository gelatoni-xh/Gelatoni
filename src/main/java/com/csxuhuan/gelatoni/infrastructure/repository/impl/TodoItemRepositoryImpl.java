package com.csxuhuan.gelatoni.infrastructure.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.csxuhuan.gelatoni.domain.model.converter.TodoItemConverter;
import com.csxuhuan.gelatoni.domain.model.entity.TodoItem;
import com.csxuhuan.gelatoni.infrastructure.repository.TodoItemRepository;
import com.csxuhuan.gelatoni.infrastructure.repository.entity.TodoItemDO;
import com.csxuhuan.gelatoni.infrastructure.repository.mapper.TodoItemMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * TODO项仓储实现
 *
 * 职责边界：
 * - 只在这里出现 TodoItemDO
 * - 负责 DO → Domain 的转换
 * - 屏蔽 MyBatis-Plus 对上层的影响
 */
@Repository
public class TodoItemRepositoryImpl implements TodoItemRepository {

    private final TodoItemMapper todoItemMapper;

    public TodoItemRepositoryImpl(TodoItemMapper todoItemMapper) {
        this.todoItemMapper = todoItemMapper;
    }

    @Override
    public List<TodoItem> findAll() {
        LambdaQueryWrapper<TodoItemDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TodoItemDO::getIsDeleted, false)
                .orderByDesc(TodoItemDO::getCreateTime);

        List<TodoItemDO> itemDOList = todoItemMapper.selectList(wrapper);

        return itemDOList.stream()
                .map(TodoItemConverter::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<TodoItem> findByTagId(Long tagId) {
        LambdaQueryWrapper<TodoItemDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TodoItemDO::getTagId, tagId)
                .eq(TodoItemDO::getIsDeleted, false)
                .orderByDesc(TodoItemDO::getCreateTime);

        List<TodoItemDO> itemDOList = todoItemMapper.selectList(wrapper);

        return itemDOList.stream()
                .map(TodoItemConverter::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public TodoItem findById(Long id) {
        LambdaQueryWrapper<TodoItemDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TodoItemDO::getId, id)
                .eq(TodoItemDO::getIsDeleted, false);

        TodoItemDO itemDO = todoItemMapper.selectOne(wrapper);
        return TodoItemConverter.toDomain(itemDO);
    }

    @Override
    public int create(TodoItem item) {
        return todoItemMapper.insert(TodoItemConverter.toDO(item));
    }

    @Override
    public int update(TodoItem item) {
        LambdaUpdateWrapper<TodoItemDO> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(TodoItemDO::getId, item.getId())
                .eq(TodoItemDO::getIsDeleted, false);

        TodoItemDO itemDO = new TodoItemDO();
        itemDO.setContent(item.getContent());
        itemDO.setCompleted(item.getCompleted());
        itemDO.setTagId(item.getTagId());

        return todoItemMapper.update(itemDO, wrapper);
    }
}
