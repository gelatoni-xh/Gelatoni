package com.csxuhuan.gelatoni.infrastructure.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.csxuhuan.gelatoni.domain.model.common.DeletedEnum;
import com.csxuhuan.gelatoni.domain.model.converter.PermissionConverter;
import com.csxuhuan.gelatoni.domain.model.entity.Permission;
import com.csxuhuan.gelatoni.infrastructure.repository.PermissionRepository;
import com.csxuhuan.gelatoni.infrastructure.repository.entity.PermissionDO;
import com.csxuhuan.gelatoni.infrastructure.repository.mapper.PermissionMapper;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 权限仓储实现
 *
 * <p>实现 {@link PermissionRepository} 接口，使用 MyBatis-Plus 进行数据访问。
 *
 * <p>职责边界（DDD 仓储模式）：
 * <ul>
 *     <li>PermissionDO 只在此类中出现，不向上层暴露</li>
 *     <li>负责 DO ↔ Domain 的转换（通过 Converter）</li>
 *     <li>屏蔽 MyBatis-Plus 的实现细节，上层只看到领域对象</li>
 * </ul>
 *
 * @author csxuhuan
 */
@Repository
public class PermissionRepositoryImpl implements PermissionRepository {

    private final PermissionMapper permissionMapper;

    /**
     * 构造函数，注入 Mapper
     *
     * @param permissionMapper 权限 Mapper
     */
    public PermissionRepositoryImpl(PermissionMapper permissionMapper) {
        this.permissionMapper = permissionMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Permission> findByIds(List<Long> permissionIds) {
        if (permissionIds == null || permissionIds.isEmpty()) {
            return Collections.emptyList();
        }
        LambdaQueryWrapper<PermissionDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(PermissionDO::getId, permissionIds)
                .eq(PermissionDO::getIsDeleted, DeletedEnum.NOT_DELETED.getValue());
        List<PermissionDO> permissionDOList = permissionMapper.selectList(wrapper);
        return permissionDOList.stream()
                .map(PermissionConverter::toDomain)
                .collect(Collectors.toList());
    }
}
