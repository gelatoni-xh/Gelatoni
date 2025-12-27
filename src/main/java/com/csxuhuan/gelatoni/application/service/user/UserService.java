package com.csxuhuan.gelatoni.application.service.user;

import com.csxuhuan.gelatoni.domain.model.user.User;

import java.util.List;

/**
 * 用户服务接口
 * 提供用户相关的业务操作方法
 */
public interface UserService {
    /**
     * 获取所有用户信息
     *
     * @return 用户列表，包含系统中所有用户的详细信息
     */
    List<User> getAllUsers();

    /**
     * 添加新用户
     *
     * @param user 待添加的用户对象，不能为空
     */
    void addUser(User user);
}
