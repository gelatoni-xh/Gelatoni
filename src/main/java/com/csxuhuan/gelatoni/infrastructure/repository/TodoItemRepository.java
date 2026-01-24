package com.csxuhuan.gelatoni.infrastructure.repository;

import com.csxuhuan.gelatoni.domain.model.entity.TodoItem;

import java.util.List;

/**
 * TODO项仓储接口
 *
 * 约定：
 * - Repository 内部使用 DO + MyBatis-Plus
 * - 对外只返回领域对象 TodoItem
 */
public interface TodoItemRepository {

    /**
     * 查询所有TODO项
     *
     * @return TODO项列表（领域对象）
     */
    List<TodoItem> findAll();

    /**
     * 根据标签ID查询TODO项
     *
     * @param tagId 标签ID
     * @return TODO项列表（领域对象）
     */
    List<TodoItem> findByTagId(Long tagId);

    /**
     * 根据ID查询TODO项
     *
     * @param id TODO项ID
     * @return TODO项（领域对象）
     */
    TodoItem findById(Long id);

    /**
     * 新增TODO项
     *
     * @param item 新增的领域对象
     * @return 新增的ID
     */
    int create(TodoItem item);

    /**
     * 更新TODO项
     *
     * @param item 更新的领域对象
     * @return 受影响行数
     */
    int update(TodoItem item);
}
