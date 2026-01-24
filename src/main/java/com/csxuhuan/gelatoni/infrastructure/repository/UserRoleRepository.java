package com.csxuhuan.gelatoni.infrastructure.repository;

import java.util.List;

/**
 * 用户角色关联仓储接口
 *
 * 约定：
 * - Repository 内部使用 DO + MyBatis-Plus
 * - 对外只返回业务相关的数据
 */
public interface UserRoleRepository {

    /**
     * 根据用户ID查询角色ID列表
     *
     * @param userId 用户ID
     * @return 角色ID列表
     */
    List<Long> findRoleIdsByUserId(Long userId);
}
