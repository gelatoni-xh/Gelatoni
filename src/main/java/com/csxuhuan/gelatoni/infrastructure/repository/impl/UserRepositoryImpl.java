package com.csxuhuan.gelatoni.infrastructure.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.csxuhuan.gelatoni.domain.model.common.DeletedEnum;
import com.csxuhuan.gelatoni.domain.model.converter.UserConverter;
import com.csxuhuan.gelatoni.domain.model.entity.User;
import com.csxuhuan.gelatoni.infrastructure.repository.UserRepository;
import com.csxuhuan.gelatoni.infrastructure.repository.entity.UserDO;
import com.csxuhuan.gelatoni.infrastructure.repository.mapper.UserMapper;
import org.springframework.stereotype.Repository;

/**
 * 用户仓储实现
 *
 * <p>实现 {@link UserRepository} 接口，使用 MyBatis-Plus 进行数据访问。
 *
 * <p>职责边界（DDD 仓储模式）：
 * <ul>
 *     <li>UserDO 只在此类中出现，不向上层暴露</li>
 *     <li>负责 DO ↔ Domain 的转换（通过 Converter）</li>
 *     <li>屏蔽 MyBatis-Plus 的实现细节，上层只看到领域对象</li>
 * </ul>
 *
 * @author csxuhuan
 */
@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserMapper userMapper;

    /**
     * 构造函数，注入 Mapper
     *
     * @param userMapper 用户 Mapper
     */
    public UserRepositoryImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public java.util.List<User> findAll() {
        LambdaQueryWrapper<UserDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserDO::getIsDeleted, DeletedEnum.NOT_DELETED.getValue())
                .orderByDesc(UserDO::getCreateTime);
        java.util.List<UserDO> userDOList = userMapper.selectList(wrapper);
        return userDOList.stream()
                .map(UserConverter::toDomain)
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User findByUsername(String username) {
        LambdaQueryWrapper<UserDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserDO::getUsername, username)
                .eq(UserDO::getIsDeleted, DeletedEnum.NOT_DELETED.getValue());
        UserDO userDO = userMapper.selectOne(wrapper);
        return UserConverter.toDomain(userDO);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User findById(Long id) {
        LambdaQueryWrapper<UserDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserDO::getId, id)
                .eq(UserDO::getIsDeleted, DeletedEnum.NOT_DELETED.getValue());
        UserDO userDO = userMapper.selectOne(wrapper);
        return UserConverter.toDomain(userDO);
    }
}
