package com.csxuhuan.gelatoni.domain.service;

import com.csxuhuan.gelatoni.domain.model.entity.TodoTag;
import com.csxuhuan.gelatoni.domain.query.TodoTagCreateQuery;

import java.util.List;

/**
 * 标签领域服务
 *
 * 说明：
 * - 负责标签相关的领域行为
 */
public interface TodoTagDomainService {

    /**
     * 查询所有标签
     *
     * @return 标签列表
     */
    List<TodoTag> findAll();

    /**
     * 创建标签
     *
     * @param query 创建条件
     * @return 创建结果
     */
    int create(TodoTagCreateQuery query);
}
