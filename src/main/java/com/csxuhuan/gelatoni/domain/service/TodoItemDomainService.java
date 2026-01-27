package com.csxuhuan.gelatoni.domain.service;

import com.csxuhuan.gelatoni.domain.model.entity.TodoItem;
import com.csxuhuan.gelatoni.domain.query.TodoItemCreateQuery;
import com.csxuhuan.gelatoni.domain.query.TodoItemUpdateQuery;

import java.util.List;

/**
 * TODO 项领域服务接口
 *
 * <p>定义 TODO 项相关的领域行为，包括查询、创建和更新操作。
 *
 * @author csxuhuan
 * @see com.csxuhuan.gelatoni.domain.service.impl.TodoItemDomainServiceImpl
 */
public interface TodoItemDomainService {

    /**
     * 查询所有 TODO 项
     *
     * <p>返回所有未删除的 TODO 项，按创建时间倒序排列。
     *
     * @param userId 用户ID，用于筛选
     * @return TODO 项列表
     */
    List<TodoItem> findAll(Long userId);

    /**
     * 根据标签 ID 查询 TODO 项
     *
     * <p>查询指定标签下的所有未删除 TODO 项。
     *
     * @param tagId 标签 ID
     * @param userId 用户ID，用于筛选
     * @return 该标签下的 TODO 项列表
     */
    List<TodoItem> findByTagId(Long tagId, Long userId);

    /**
     * 创建 TODO 项
     *
     * @param query 创建条件
     * @param userId 用户ID
     * @param creator 创建人ID
     * @return 影响的行数
     */
    int create(TodoItemCreateQuery query, Long userId, Long creator);

    /**
     * 更新 TODO 项
     *
     * @param query 更新条件
     * @param userId 用户ID，用于权限校验
     * @param modifier 修改人ID
     * @return 影响的行数
     */
    int update(TodoItemUpdateQuery query, Long userId, Long modifier);

    /**
     * 删除 TODO 项
     *
     * @param id TODO项ID
     * @param userId 用户ID，用于权限校验
     * @param modifier 修改人ID
     * @return 影响的行数
     */
    int delete(Long id, Long userId, Long modifier);
}
