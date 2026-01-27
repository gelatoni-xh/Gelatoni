package com.csxuhuan.gelatoni.application.service;

import com.csxuhuan.gelatoni.domain.model.entity.TodoItem;
import com.csxuhuan.gelatoni.domain.model.entity.TodoTag;
import com.csxuhuan.gelatoni.domain.query.TodoItemCreateQuery;
import com.csxuhuan.gelatoni.domain.query.TodoItemUpdateQuery;
import com.csxuhuan.gelatoni.domain.query.TodoTagCreateQuery;

import java.util.List;

/**
 * TODO 应用服务接口
 *
 * <p>应用层服务，提供 TODO 待办事项和标签相关的业务操作。
 * 作为 interfaces 层与 domain 层之间的桥梁。
 *
 * <p>支持的操作：
 * <ul>
 *     <li>查询所有 TODO 项</li>
 *     <li>按标签筛选 TODO 项</li>
 *     <li>创建新的 TODO 项</li>
 *     <li>更新已有的 TODO 项</li>
 *     <li>查询所有标签</li>
 *     <li>创建新标签</li>
 * </ul>
 *
 * @author csxuhuan
 * @see com.csxuhuan.gelatoni.application.service.impl.TodoAppServiceImpl
 */
public interface TodoAppService {

    // ========== TODO 项相关方法 ==========

    /**
     * 查询所有 TODO 项
     *
     * <p>返回所有未删除的 TODO 项，按创建时间倒序排列。
     *
     * @param userId 用户ID，用于筛选
     * @return TODO 项列表
     */
    List<TodoItem> findAllItems(Long userId);

    /**
     * 根据标签 ID 查询 TODO 项
     *
     * <p>查询指定标签下的所有未删除 TODO 项，按创建时间倒序排列。
     *
     * @param tagId 标签 ID
     * @param userId 用户ID，用于筛选
     * @return 该标签下的 TODO 项列表
     */
    List<TodoItem> findItemsByTagId(Long tagId, Long userId);

    /**
     * 创建 TODO 项
     *
     * <p>创建一个新的 TODO 项，默认状态为未完成。
     *
     * @param query 创建条件，包含 content 和可选的 tagId
     * @param userId 用户ID
     * @return 影响的行数
     */
    int createItem(TodoItemCreateQuery query, Long userId);

    /**
     * 更新 TODO 项
     *
     * <p>更新指定 TODO 项的信息，支持部分更新。
     *
     * @param query 更新条件，包含 id（必填）及可选的 content、completed、tagId
     * @param userId 用户ID，用于权限校验
     * @return 影响的行数
     */
    int updateItem(TodoItemUpdateQuery query, Long userId);

    /**
     * 删除 TODO 项
     *
     * @param id TODO项ID
     * @param userId 用户ID，用于权限校验
     * @return 影响的行数
     */
    int deleteItem(Long id, Long userId);

    // ========== TODO 标签相关方法 ==========

    /**
     * 查询所有标签
     *
     * <p>返回所有未删除的标签，按创建时间倒序排列。
     *
     * @param userId 用户ID，用于筛选
     * @return 标签列表
     */
    List<TodoTag> findAllTags(Long userId);

    /**
     * 创建标签
     *
     * <p>创建一个新的 TODO 标签。
     *
     * @param query 创建条件，包含 name（标签名称）
     * @param userId 用户ID
     * @return 影响的行数
     */
    int createTag(TodoTagCreateQuery query, Long userId);

    /**
     * 删除标签
     *
     * @param id 标签ID
     * @param userId 用户ID，用于权限校验
     * @return 影响的行数
     */
    int deleteTag(Long id, Long userId);
}
