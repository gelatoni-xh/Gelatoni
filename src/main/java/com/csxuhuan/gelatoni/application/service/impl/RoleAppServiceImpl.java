package com.csxuhuan.gelatoni.application.service.impl;

import com.csxuhuan.gelatoni.application.service.RoleAppService;
import com.csxuhuan.gelatoni.domain.model.entity.Role;
import com.csxuhuan.gelatoni.domain.query.RoleCreateOrUpdateQuery;
import com.csxuhuan.gelatoni.domain.service.RoleDomainService;
import com.csxuhuan.gelatoni.infrastructure.repository.RolePermissionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色应用服务实现
 */
@Service
public class RoleAppServiceImpl implements RoleAppService {

    private final RoleDomainService roleDomainService;
    private final RolePermissionRepository rolePermissionRepository;

    public RoleAppServiceImpl(RoleDomainService roleDomainService,
                              RolePermissionRepository rolePermissionRepository) {
        this.roleDomainService = roleDomainService;
        this.rolePermissionRepository = rolePermissionRepository;
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
}
