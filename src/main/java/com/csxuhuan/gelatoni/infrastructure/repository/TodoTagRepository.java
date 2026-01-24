package com.csxuhuan.gelatoni.infrastructure.repository;

import com.csxuhuan.gelatoni.domain.model.entity.TodoTag;

import java.util.List;

/**
 * 标签仓储接口
 *
 * 约定：
 * - Repository 内部使用 DO + MyBatis-Plus
 * - 对外只返回领域对象 TodoTag
 */
public interface TodoTagRepository {

    /**
     * 查询所有标签
     *
     * @return 标签列表（领域对象）
     */
    List<TodoTag> findAll();

    /**
     * 根据ID查询标签
     *
     * @param id 标签ID
     * @return 标签（领域对象）
     */
    TodoTag findById(Long id);

    /**
     * 新增标签
     *
     * @param tag 新增的领域对象
     * @return 新增的ID
     */
    int create(TodoTag tag);
}
