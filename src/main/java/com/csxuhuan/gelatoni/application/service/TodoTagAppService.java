package com.csxuhuan.gelatoni.application.service;

import com.csxuhuan.gelatoni.domain.model.entity.TodoTag;
import com.csxuhuan.gelatoni.domain.query.TodoTagCreateQuery;

import java.util.List;

/**
 * TODO 标签应用服务接口
 *
 * <p>应用层服务，提供 TODO 标签相关的业务操作。
 * 标签用于对 TODO 项进行分类管理。
 *
 * <p>支持的操作：
 * <ul>
 *     <li>查询所有标签</li>
 *     <li>创建新标签</li>
 * </ul>
 *
 * @author csxuhuan
 * @see com.csxuhuan.gelatoni.application.service.impl.TodoTagAppServiceImpl
 */
public interface TodoTagAppService {

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
     * <p>创建一个新的 TODO 标签。
     *
     * @param query 创建条件，包含 name（标签名称）
     * @return 影响的行数
     */
    int create(TodoTagCreateQuery query);
}
