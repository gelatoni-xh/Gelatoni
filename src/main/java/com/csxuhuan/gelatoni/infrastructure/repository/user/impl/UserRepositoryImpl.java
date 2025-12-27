package com.csxuhuan.gelatoni.infrastructure.repository.user.impl;

import com.csxuhuan.gelatoni.domain.model.user.User;
import com.csxuhuan.gelatoni.infrastructure.repository.user.UserRepository;

import java.util.List;

/**
 * 用户仓库实现类
 * 提供用户数据访问的具体实现
 */
public class UserRepositoryImpl implements UserRepository {
    /**
     * 查询所有用户
     *
     * @return 用户列表，当前实现返回null
     */
    @Override
    public List<User> findAll() {
        return null;
    }

    /**
     * 保存用户信息
     *
     * @param user 要保存的用户对象
     */
    @Override
    public void save(User user) {
        System.out.println("保存用户：" + user);
    }

}
