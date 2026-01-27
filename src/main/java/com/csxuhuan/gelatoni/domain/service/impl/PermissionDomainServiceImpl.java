package com.csxuhuan.gelatoni.domain.service.impl;

import com.csxuhuan.gelatoni.domain.model.entity.Permission;
import com.csxuhuan.gelatoni.domain.query.PermissionCreateOrUpdateQuery;
import com.csxuhuan.gelatoni.domain.service.PermissionDomainService;
import com.csxuhuan.gelatoni.infrastructure.repository.PermissionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 权限领域服务实现
 */
@Service
public class PermissionDomainServiceImpl implements PermissionDomainService {

    private final PermissionRepository permissionRepository;

    public PermissionDomainServiceImpl(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Override
    public List<Permission> findAll() {
        return permissionRepository.findAll();
    }

    @Override
    public int create(PermissionCreateOrUpdateQuery query, Long creator) {
        Permission permission = query.toPermission();
        return permissionRepository.create(permission, creator);
    }

    @Override
    public int update(PermissionCreateOrUpdateQuery query, Long modifier) {
        Permission permission = query.toPermission();
        return permissionRepository.update(permission, modifier);
    }
}
