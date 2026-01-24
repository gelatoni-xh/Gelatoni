package com.csxuhuan.gelatoni.infrastructure.repository;

import com.csxuhuan.gelatoni.domain.model.entity.Role;

import java.util.List;

/**
 * 角色仓储接口
 *
 * 约定：
 * - Repository 内部使用 DO + MyBatis-Plus
 * - 对外只返回领域对象 Role
 */
public interface RoleRepository {

    /**
     * 根据角色ID列表查询角色列表
     *
     * @param roleIds 角色ID列表
     * @return 角色领域对象列表
     */
    List<Role> findByIds(List<Long> roleIds);
}
