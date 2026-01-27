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
     * 查询所有角色
     *
     * @return 角色领域对象列表
     */
    List<Role> findAll();

    /**
     * 创建角色
     *
     * @param role 角色领域对象
     * @param creator 创建人ID
     * @return 影响行数
     */
    int create(Role role, Long creator);

    /**
     * 更新角色
     *
     * @param role 角色领域对象
     * @param modifier 修改人ID
     * @return 影响行数
     */
    int update(Role role, Long modifier);

    /**
     * 根据角色ID列表查询角色列表
     *
     * @param roleIds 角色ID列表
     * @return 角色领域对象列表
     */
    List<Role> findByIds(List<Long> roleIds);

    /**
     * 根据角色编码查询角色
     *
     * @param roleCode 角色编码
     * @return 角色领域对象，如果不存在则返回 null
     */
    Role findByRoleCode(String roleCode);
}
