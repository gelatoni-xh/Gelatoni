package com.csxuhuan.gelatoni.application.service;

import com.csxuhuan.gelatoni.domain.model.entity.Permission;
import com.csxuhuan.gelatoni.domain.query.PermissionCreateOrUpdateQuery;

import java.util.List;

/**
 * 权限应用服务接口
 */
public interface PermissionAppService {

    /**
     * 查询所有权限
     *
     * @return 权限列表
     */
    List<Permission> findAllPermissions();

    /**
     * 创建权限
     *
     * @param query 创建条件
     * @param operator 操作人ID
     * @return 影响行数
     */
    int createPermission(PermissionCreateOrUpdateQuery query, Long operator);

    /**
     * 更新权限
     *
     * @param query 更新条件
     * @param operator 操作人ID
     * @return 影响行数
     */
    int updatePermission(PermissionCreateOrUpdateQuery query, Long operator);
}
