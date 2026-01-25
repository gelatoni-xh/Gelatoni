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
     * @param userId 用户ID，用于筛选
     * @return TODO项列表（领域对象）
     */
    List<TodoItem> findAll(Long userId);

    /**
     * 根据标签ID查询TODO项
     *
     * @param tagId 标签ID
     * @param userId 用户ID，用于筛选
     * @return TODO项列表（领域对象）
     */
    List<TodoItem> findByTagId(Long tagId, Long userId);

    /**
     * 根据ID查询TODO项
     *
     * @param id TODO项ID
     * @param userId 用户ID，用于权限校验
     * @return TODO项（领域对象）
     */
    TodoItem findById(Long id, Long userId);

    /**
     * 新增TODO项
     *
     * @param item 新增的领域对象
     * @param userId 用户ID
     * @param creator 创建人ID
     * @return 新增的ID
     */
    int create(TodoItem item, Long userId, Long creator);

    /**
     * 更新TODO项
     *
     * @param item 更新的领域对象
     * @param userId 用户ID，用于权限校验
     * @param modifier 修改人ID
     * @return 受影响行数
     */
    int update(TodoItem item, Long userId, Long modifier);
}
