package com.csxuhuan.gelatoni.domain.service;

import com.csxuhuan.gelatoni.domain.model.entity.TodoItem;
import com.csxuhuan.gelatoni.domain.query.TodoItemCreateQuery;
import com.csxuhuan.gelatoni.domain.query.TodoItemUpdateQuery;

import java.util.List;

/**
 * TODO项领域服务
 *
 * 说明：
 * - 负责TODO项相关的领域行为
 */
public interface TodoItemDomainService {

    /**
     * 查询所有TODO项
     *
     * @return TODO项列表
     */
    List<TodoItem> findAll();

    /**
     * 根据标签ID查询TODO项
     *
     * @param tagId 标签ID
     * @return TODO项列表
     */
    List<TodoItem> findByTagId(Long tagId);

    /**
     * 创建TODO项
     *
     * @param query 创建条件
     * @return 创建结果
     */
    int create(TodoItemCreateQuery query);

    /**
     * 更新TODO项
     *
     * @param query 更新条件
     * @return 更新结果
     */
    int update(TodoItemUpdateQuery query);
}
