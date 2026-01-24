package com.csxuhuan.gelatoni.application.service;

import com.csxuhuan.gelatoni.domain.model.entity.TodoTag;
import com.csxuhuan.gelatoni.domain.query.TodoTagCreateQuery;

import java.util.List;

/**
 * 标签应用服务接口
 * 提供标签相关的业务操作
 */
public interface TodoTagAppService {

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
