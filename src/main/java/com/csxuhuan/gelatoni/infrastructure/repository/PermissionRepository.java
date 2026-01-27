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
     * 查询所有权限
     *
     * @return 权限领域对象列表
     */
    List<Permission> findAll();

    /**
     * 创建权限
     *
     * @param permission 权限领域对象
     * @param creator 创建人ID
     * @return 影响行数
     */
    int create(Permission permission, Long creator);

    /**
     * 更新权限
     *
     * @param permission 权限领域对象
     * @param modifier 修改人ID
     * @return 影响行数
     */
    int update(Permission permission, Long modifier);

    /**
     * 根据权限ID列表查询权限列表
     *
     * @param permissionIds 权限ID列表
     * @return 权限领域对象列表
     */
    List<Permission> findByIds(List<Long> permissionIds);
}
