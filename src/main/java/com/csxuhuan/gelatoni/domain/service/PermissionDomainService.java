package com.csxuhuan.gelatoni.domain.service;

import com.csxuhuan.gelatoni.domain.model.entity.Permission;
import com.csxuhuan.gelatoni.domain.query.PermissionCreateOrUpdateQuery;

import java.util.List;

/**
 * 权限领域服务接口
 */
public interface PermissionDomainService {

    /**
     * 查询所有权限
     *
     * @return 权限领域对象列表
     */
    List<Permission> findAll();

    /**
     * 创建权限
     *
     * @param query 创建条件
     * @param creator 创建人ID
     * @return 影响行数
     */
    int create(PermissionCreateOrUpdateQuery query, Long creator);

    /**
     * 更新权限
     *
     * @param query 更新条件
     * @param modifier 修改人ID
     * @return 影响行数
     */
    int update(PermissionCreateOrUpdateQuery query, Long modifier);
}
