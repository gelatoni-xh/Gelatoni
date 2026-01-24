package com.csxuhuan.gelatoni.infrastructure.repository;

import java.util.List;

/**
 * 角色权限关联仓储接口
 *
 * 约定：
 * - Repository 内部使用 DO + MyBatis-Plus
 * - 对外只返回业务相关的数据
 */
public interface RolePermissionRepository {

    /**
     * 根据角色ID列表查询权限ID列表
     *
     * @param roleIds 角色ID列表
     * @return 权限ID列表
     */
    List<Long> findPermissionIdsByRoleIds(List<Long> roleIds);
}
