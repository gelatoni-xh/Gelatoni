package com.csxuhuan.gelatoni.domain.service;

import com.csxuhuan.gelatoni.domain.model.entity.TodoTag;
import com.csxuhuan.gelatoni.domain.query.TodoTagCreateQuery;

import java.util.List;

/**
 * TODO 标签领域服务接口
 *
 * <p>定义标签相关的领域行为，标签用于对 TODO 项进行分类管理。
 *
 * @author csxuhuan
 * @see com.csxuhuan.gelatoni.domain.service.impl.TodoTagDomainServiceImpl
 */
public interface TodoTagDomainService {

    /**
     * 查询所有标签
     *
     * <p>返回所有未删除的标签，按创建时间倒序排列。
     *
     * @return 标签列表
     */
    List<TodoTag> findAll();

    /**
     * 创建标签
     *
     * @param query 创建条件
     * @return 影响的行数
     */
    int create(TodoTagCreateQuery query);
}
