package com.csxuhuan.gelatoni.infrastructure.repository;

import com.csxuhuan.gelatoni.domain.model.entity.User;

/**
 * 用户仓储接口
 *
 * 约定：
 * - Repository 内部使用 DO + MyBatis-Plus
 * - 对外只返回领域对象 User
 */
public interface UserRepository {

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户领域对象，如果不存在则返回 null
     */
    User findByUsername(String username);

    /**
     * 根据用户ID查询用户
     *
     * @param id 用户ID
     * @return 用户领域对象，如果不存在则返回 null
     */
    User findById(Long id);
}
