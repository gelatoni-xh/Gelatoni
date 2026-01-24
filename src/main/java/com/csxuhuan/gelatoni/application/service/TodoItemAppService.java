package com.csxuhuan.gelatoni.application.service;

import com.csxuhuan.gelatoni.domain.model.entity.TodoItem;
import com.csxuhuan.gelatoni.domain.query.TodoItemCreateQuery;
import com.csxuhuan.gelatoni.domain.query.TodoItemUpdateQuery;

import java.util.List;

/**
 * TODO 项应用服务接口
 *
 * <p>应用层服务，提供 TODO 待办事项相关的业务操作。
 * 作为 interfaces 层与 domain 层之间的桥梁。
 *
 * <p>支持的操作：
 * <ul>
 *     <li>查询所有 TODO 项</li>
 *     <li>按标签筛选 TODO 项</li>
 *     <li>创建新的 TODO 项</li>
 *     <li>更新已有的 TODO 项</li>
 * </ul>
 *
 * @author csxuhuan
 * @see com.csxuhuan.gelatoni.application.service.impl.TodoItemAppServiceImpl
 */
public interface TodoItemAppService {

    /**
     * 查询所有 TODO 项
     *
     * <p>返回所有未删除的 TODO 项，按创建时间倒序排列。
     *
     * @return TODO 项列表
     */
    List<TodoItem> findAll();

    /**
     * 根据标签 ID 查询 TODO 项
     *
     * <p>查询指定标签下的所有未删除 TODO 项，按创建时间倒序排列。
     *
     * @param tagId 标签 ID
     * @return 该标签下的 TODO 项列表
     */
    List<TodoItem> findByTagId(Long tagId);

    /**
     * 创建 TODO 项
     *
     * <p>创建一个新的 TODO 项，默认状态为未完成。
     *
     * @param query 创建条件，包含 content 和可选的 tagId
     * @return 影响的行数
     */
    int create(TodoItemCreateQuery query);

    /**
     * 更新 TODO 项
     *
     * <p>更新指定 TODO 项的信息，支持部分更新。
     *
     * @param query 更新条件，包含 id（必填）及可选的 content、completed、tagId
     * @return 影响的行数
     */
    int update(TodoItemUpdateQuery query);
}
