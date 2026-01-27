package com.csxuhuan.gelatoni.application.service.impl;

import com.csxuhuan.gelatoni.application.service.RoleAppService;
import com.csxuhuan.gelatoni.domain.model.entity.Permission;
import com.csxuhuan.gelatoni.domain.model.entity.Role;
import com.csxuhuan.gelatoni.domain.query.RoleCreateOrUpdateQuery;
import com.csxuhuan.gelatoni.domain.service.RoleDomainService;
import com.csxuhuan.gelatoni.infrastructure.repository.PermissionRepository;
import com.csxuhuan.gelatoni.infrastructure.repository.RolePermissionRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * 角色应用服务实现
 */
@Service
public class RoleAppServiceImpl implements RoleAppService {

    private final RoleDomainService roleDomainService;
    private final RolePermissionRepository rolePermissionRepository;
    private final PermissionRepository permissionRepository;

    public RoleAppServiceImpl(RoleDomainService roleDomainService,
                              RolePermissionRepository rolePermissionRepository, PermissionRepository permissionRepository) {
        this.roleDomainService = roleDomainService;
        this.rolePermissionRepository = rolePermissionRepository;
        this.permissionRepository = permissionRepository;
    }

    @Override
    public List<Role> findAllRoles() {
        return roleDomainService.findAll();
    }

    @Override
    public int createRole(RoleCreateOrUpdateQuery query, Long operator) {
        return roleDomainService.create(query, operator);
    }

    @Override
    public int updateRole(RoleCreateOrUpdateQuery query, Long operator) {
        return roleDomainService.update(query, operator);
    }

    @Override
    public int assignPermissions(Long roleId, List<Long> permissionIds, Long operator) {
        rolePermissionRepository.deleteByRoleId(roleId, operator);
        return rolePermissionRepository.createBatch(roleId, permissionIds, operator);
    }

    @Override
    public List<String> getPermissionCodesByRoleId(Long roleId) {
        // 1. 根据角色ID查询权限ID列表
        List<Long> permissionIds = rolePermissionRepository.findPermissionIdsByRoleIds(Collections.singletonList(roleId));
        if (permissionIds.isEmpty()) {
            return java.util.Collections.emptyList();
        }

        // 2. 根据权限ID列表查询权限信息，提取权限码
        return permissionRepository.findByIds(permissionIds)
                .stream()
                .map(Permission::getPermissionCode)
                .collect(java.util.stream.Collectors.toList());
    }
}
