package com.csxuhuan.gelatoni.infrastructure.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.csxuhuan.gelatoni.domain.model.common.DeletedEnum;
import com.csxuhuan.gelatoni.infrastructure.repository.RolePermissionRepository;
import com.csxuhuan.gelatoni.infrastructure.repository.entity.RolePermissionDO;
import com.csxuhuan.gelatoni.infrastructure.repository.mapper.RolePermissionMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色权限关联仓储实现
 *
 * <p>实现 {@link RolePermissionRepository} 接口，使用 MyBatis-Plus 进行数据访问。
 *
 * @author csxuhuan
 */
@Repository
public class RolePermissionRepositoryImpl implements RolePermissionRepository {

    private final RolePermissionMapper rolePermissionMapper;

    /**
     * 构造函数，注入 Mapper
     *
     * @param rolePermissionMapper 角色权限关联 Mapper
     */
    public RolePermissionRepositoryImpl(RolePermissionMapper rolePermissionMapper) {
        this.rolePermissionMapper = rolePermissionMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Long> findPermissionIdsByRoleIds(List<Long> roleIds) {
        if (roleIds == null || roleIds.isEmpty()) {
            return Collections.emptyList();
        }
        LambdaQueryWrapper<RolePermissionDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(RolePermissionDO::getRoleId, roleIds)
                .eq(RolePermissionDO::getIsDeleted, DeletedEnum.NOT_DELETED.getValue());
        List<RolePermissionDO> rolePermissionDOList = rolePermissionMapper.selectList(wrapper);
        return rolePermissionDOList.stream()
                .map(RolePermissionDO::getPermissionId)
                .distinct()
                .collect(Collectors.toList());
    }
}
