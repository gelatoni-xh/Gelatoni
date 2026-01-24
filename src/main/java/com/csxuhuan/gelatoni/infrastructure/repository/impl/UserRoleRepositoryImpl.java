package com.csxuhuan.gelatoni.infrastructure.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.csxuhuan.gelatoni.domain.model.common.DeletedEnum;
import com.csxuhuan.gelatoni.infrastructure.repository.UserRoleRepository;
import com.csxuhuan.gelatoni.infrastructure.repository.entity.UserRoleDO;
import com.csxuhuan.gelatoni.infrastructure.repository.mapper.UserRoleMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户角色关联仓储实现
 *
 * <p>实现 {@link UserRoleRepository} 接口，使用 MyBatis-Plus 进行数据访问。
 *
 * @author csxuhuan
 */
@Repository
public class UserRoleRepositoryImpl implements UserRoleRepository {

    private final UserRoleMapper userRoleMapper;

    /**
     * 构造函数，注入 Mapper
     *
     * @param userRoleMapper 用户角色关联 Mapper
     */
    public UserRoleRepositoryImpl(UserRoleMapper userRoleMapper) {
        this.userRoleMapper = userRoleMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Long> findRoleIdsByUserId(Long userId) {
        LambdaQueryWrapper<UserRoleDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRoleDO::getUserId, userId)
                .eq(UserRoleDO::getIsDeleted, DeletedEnum.NOT_DELETED.getValue());
        List<UserRoleDO> userRoleDOList = userRoleMapper.selectList(wrapper);
        return userRoleDOList.stream()
                .map(UserRoleDO::getRoleId)
                .collect(Collectors.toList());
    }
}
