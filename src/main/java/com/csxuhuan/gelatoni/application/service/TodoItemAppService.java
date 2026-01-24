package com.csxuhuan.gelatoni.application.service;

import com.csxuhuan.gelatoni.domain.model.entity.TodoItem;
import com.csxuhuan.gelatoni.domain.query.TodoItemCreateQuery;
import com.csxuhuan.gelatoni.domain.query.TodoItemUpdateQuery;

import java.util.List;

/**
 * TODO项应用服务接口
 * 提供TODO项相关的业务操作
 */
public interface TodoItemAppService {

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
