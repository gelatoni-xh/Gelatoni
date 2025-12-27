package com.csxuhuan.gelatoni.infrastructure.repository.user;

import com.csxuhuan.gelatoni.domain.model.user.User;

import java.util.List;

/**
 * 用户数据访问接口
 * 定义了用户实体的基本数据访问操作
 */
public interface UserRepository {
    /**
     * 查询所有用户
     *
     * @return 包含所有用户的列表
     */
    List<User> findAll();

    /**
     * 保存用户信息到数据库
     * @param user 待保存的用户对象，包含用户的基本信息
     */
    void save(User user);

}
