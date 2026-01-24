package com.csxuhuan.gelatoni.infrastructure.repository;

import com.csxuhuan.gelatoni.domain.model.entity.Permission;

import java.util.List;

/**
 * 权限仓储接口
 *
 * 约定：
 * - Repository 内部使用 DO + MyBatis-Plus
 * - 对外只返回领域对象 Permission
 */
public interface PermissionRepository {

    /**
     * 根据权限ID列表查询权限列表
     *
     * @param permissionIds 权限ID列表
     * @return 权限领域对象列表
     */
    List<Permission> findByIds(List<Long> permissionIds);
}
