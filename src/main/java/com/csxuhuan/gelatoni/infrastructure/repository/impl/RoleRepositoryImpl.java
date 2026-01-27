package com.csxuhuan.gelatoni.infrastructure.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.csxuhuan.gelatoni.domain.model.common.DeletedEnum;
import com.csxuhuan.gelatoni.domain.model.converter.RoleConverter;
import com.csxuhuan.gelatoni.domain.model.entity.Role;
import com.csxuhuan.gelatoni.infrastructure.repository.RoleRepository;
import com.csxuhuan.gelatoni.infrastructure.repository.entity.RoleDO;
import com.csxuhuan.gelatoni.infrastructure.repository.mapper.RoleMapper;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色仓储实现
 *
 * <p>实现 {@link RoleRepository} 接口，使用 MyBatis-Plus 进行数据访问。
 *
 * <p>职责边界（DDD 仓储模式）：
 * <ul>
 *     <li>RoleDO 只在此类中出现，不向上层暴露</li>
 *     <li>负责 DO ↔ Domain 的转换（通过 Converter）</li>
 *     <li>屏蔽 MyBatis-Plus 的实现细节，上层只看到领域对象</li>
 * </ul>
 *
 * @author csxuhuan
 */
@Repository
public class RoleRepositoryImpl implements RoleRepository {

    private final RoleMapper roleMapper;

    /**
     * 构造函数，注入 Mapper
     *
     * @param roleMapper 角色 Mapper
     */
    public RoleRepositoryImpl(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Role> findAll() {
        LambdaQueryWrapper<RoleDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleDO::getIsDeleted, DeletedEnum.NOT_DELETED.getValue())
                .orderByDesc(RoleDO::getCreateTime);
        List<RoleDO> roleDOList = roleMapper.selectList(wrapper);
        return roleDOList.stream()
                .map(RoleConverter::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int create(Role role, Long creator) {
        RoleDO roleDO = RoleConverter.toDO(role);
        roleDO.setCreator(creator);
        roleDO.setModifier(creator);
        return roleMapper.insert(roleDO);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int update(Role role, Long modifier) {
        LambdaQueryWrapper<RoleDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleDO::getId, role.getId())
                .eq(RoleDO::getIsDeleted, DeletedEnum.NOT_DELETED.getValue());

        RoleDO roleDO = new RoleDO();
        roleDO.setRoleCode(role.getRoleCode());
        roleDO.setRoleName(role.getRoleName());
        roleDO.setStatus(role.getStatus());
        roleDO.setModifier(modifier);

        return roleMapper.update(roleDO, wrapper);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Role> findByIds(List<Long> roleIds) {
        if (roleIds == null || roleIds.isEmpty()) {
            return Collections.emptyList();
        }
        LambdaQueryWrapper<RoleDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(RoleDO::getId, roleIds)
                .eq(RoleDO::getIsDeleted, DeletedEnum.NOT_DELETED.getValue());
        List<RoleDO> roleDOList = roleMapper.selectList(wrapper);
        return roleDOList.stream()
                .map(RoleConverter::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Role findByRoleCode(String roleCode) {
        if (roleCode == null || roleCode.isEmpty()) {
            return null;
        }
        LambdaQueryWrapper<RoleDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleDO::getRoleCode, roleCode)
                .eq(RoleDO::getIsDeleted, DeletedEnum.NOT_DELETED.getValue());
        RoleDO roleDO = roleMapper.selectOne(wrapper);
        return roleDO != null ? RoleConverter.toDomain(roleDO) : null;
    }
}
