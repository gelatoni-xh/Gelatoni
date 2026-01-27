package com.csxuhuan.gelatoni.application.service;

import com.csxuhuan.gelatoni.domain.model.entity.Role;
import com.csxuhuan.gelatoni.domain.query.RoleCreateOrUpdateQuery;

import java.util.List;

/**
 * 角色应用服务接口
 */
public interface RoleAppService {

    /**
     * 查询所有角色
     *
     * @return 角色列表
     */
    List<Role> findAllRoles();

    /**
     * 创建角色
     *
     * @param query 创建条件
     * @param operator 操作人ID
     * @return 影响行数
     */
    int createRole(RoleCreateOrUpdateQuery query, Long operator);

    /**
     * 更新角色
     *
     * @param query 更新条件
     * @param operator 操作人ID
     * @return 影响行数
     */
    int updateRole(RoleCreateOrUpdateQuery query, Long operator);

    /**
     * 给角色配置权限（覆盖式）
     *
     * @param roleId 角色ID
     * @param permissionIds 权限ID列表
     * @param operator 操作人ID
     * @return 影响行数
     */
    int assignPermissions(Long roleId, List<Long> permissionIds, Long operator);
}
