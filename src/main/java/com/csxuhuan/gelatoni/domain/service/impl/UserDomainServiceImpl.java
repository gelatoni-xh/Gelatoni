package com.csxuhuan.gelatoni.domain.service.impl;

import com.csxuhuan.gelatoni.domain.model.entity.User;
import com.csxuhuan.gelatoni.domain.service.UserDomainService;
import com.csxuhuan.gelatoni.infrastructure.repository.UserRepository;
import org.springframework.stereotype.Service;

/**
 * 用户领域服务实现
 *
 * <p>实现 {@link UserDomainService} 接口，处理用户相关的领域逻辑。
 * 通过 {@link UserRepository} 访问数据，屏蔽持久化实现细节。
 *
 * @author csxuhuan
 */
@Service
public class UserDomainServiceImpl implements UserDomainService {

    private final UserRepository userRepository;

    /**
     * 构造函数，注入仓储接口
     *
     * @param userRepository 用户仓储
     */
    public UserDomainServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public java.util.List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User findById(Long id) {
        return userRepository.findById(id);
    }
}
