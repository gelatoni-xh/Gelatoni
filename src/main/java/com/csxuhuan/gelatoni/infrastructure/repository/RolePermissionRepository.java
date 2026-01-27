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

    /**
     * 删除角色的所有权限关联（软删除）
     *
     * @param roleId 角色ID
     * @param modifier 修改人ID
     * @return 影响行数
     */
    int deleteByRoleId(Long roleId, Long modifier);

    /**
     * 批量创建角色权限关联
     *
     * @param roleId 角色ID
     * @param permissionIds 权限ID列表
     * @param creator 创建人ID
     * @return 影响行数
     */
    int createBatch(Long roleId, List<Long> permissionIds, Long creator);
}
