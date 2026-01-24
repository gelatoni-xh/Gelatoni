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
 * TODO 项仓储实现
 *
 * <p>实现 {@link TodoItemRepository} 接口，使用 MyBatis-Plus 进行数据访问。
 *
 * <p>查询约定：
 * <ul>
 *     <li>所有查询默认过滤已删除的记录（isDeleted = false）</li>
 *     <li>列表查询按创建时间倒序排列</li>
 * </ul>
 *
 * @author csxuhuan
 */
@Repository
public class TodoItemRepositoryImpl implements TodoItemRepository {

    private final TodoItemMapper todoItemMapper;

    /**
     * 构造函数，注入 Mapper
     *
     * @param todoItemMapper TODO 项 Mapper
     */
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
