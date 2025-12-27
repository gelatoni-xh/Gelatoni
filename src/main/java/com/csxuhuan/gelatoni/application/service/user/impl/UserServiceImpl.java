package com.csxuhuan.gelatoni.application.service.user.impl;

import com.csxuhuan.gelatoni.application.service.user.UserService;
import com.csxuhuan.gelatoni.domain.model.user.User;
import com.csxuhuan.gelatoni.infrastructure.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 用户服务实现类
 * 提供用户相关的业务操作实现
 */
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    /**
     * 获取所有用户信息
     *
     * @return 用户列表，包含系统中所有用户对象
     */
    @Override
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    /**
     * 添加新用户
     *
     * @param user 待添加的用户对象，包含用户的基本信息
     */
    @Override
    public void addUser(User user) {
        repository.save(user);
    }
}
