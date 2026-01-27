package com.csxuhuan.gelatoni.domain.service.impl;

import com.csxuhuan.gelatoni.domain.model.entity.Role;
import com.csxuhuan.gelatoni.domain.query.RoleCreateOrUpdateQuery;
import com.csxuhuan.gelatoni.domain.service.RoleDomainService;
import com.csxuhuan.gelatoni.infrastructure.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色领域服务实现
 */
@Service
public class RoleDomainServiceImpl implements RoleDomainService {

    private final RoleRepository roleRepository;

    public RoleDomainServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public int create(RoleCreateOrUpdateQuery query, Long creator) {
        Role role = query.toRole();
        return roleRepository.create(role, creator);
    }

    @Override
    public int update(RoleCreateOrUpdateQuery query, Long modifier) {
        Role role = query.toRole();
        return roleRepository.update(role, modifier);
    }
}
