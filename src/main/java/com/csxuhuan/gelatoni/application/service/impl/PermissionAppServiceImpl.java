package com.csxuhuan.gelatoni.application.service.impl;

import com.csxuhuan.gelatoni.application.service.PermissionAppService;
import com.csxuhuan.gelatoni.domain.model.entity.Permission;
import com.csxuhuan.gelatoni.domain.query.PermissionCreateOrUpdateQuery;
import com.csxuhuan.gelatoni.domain.service.PermissionDomainService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 权限应用服务实现
 */
@Service
public class PermissionAppServiceImpl implements PermissionAppService {

    private final PermissionDomainService permissionDomainService;

    public PermissionAppServiceImpl(PermissionDomainService permissionDomainService) {
        this.permissionDomainService = permissionDomainService;
    }

    @Override
    public List<Permission> findAllPermissions() {
        return permissionDomainService.findAll();
    }

    @Override
    public int createPermission(PermissionCreateOrUpdateQuery query, Long operator) {
        return permissionDomainService.create(query, operator);
    }

    @Override
    public int updatePermission(PermissionCreateOrUpdateQuery query, Long operator) {
        return permissionDomainService.update(query, operator);
    }
}
