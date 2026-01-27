package com.csxuhuan.gelatoni.domain.service;

import com.csxuhuan.gelatoni.domain.model.entity.Role;
import com.csxuhuan.gelatoni.domain.query.RoleCreateOrUpdateQuery;

import java.util.List;

/**
 * 角色领域服务接口
 */
public interface RoleDomainService {

    /**
     * 查询所有角色
     *
     * @return 角色领域对象列表
     */
    List<Role> findAll();

    /**
     * 创建角色
     *
     * @param query 创建或更新条件
     * @param creator 创建人ID
     * @return 影响行数
     */
    int create(RoleCreateOrUpdateQuery query, Long creator);

    /**
     * 更新角色
     *
     * @param query 创建或更新条件
     * @param modifier 修改人ID
     * @return 影响行数
     */
    int update(RoleCreateOrUpdateQuery query, Long modifier);
}
