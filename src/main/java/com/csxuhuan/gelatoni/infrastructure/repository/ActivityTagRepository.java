package com.csxuhuan.gelatoni.infrastructure.repository;

import com.csxuhuan.gelatoni.domain.model.entity.ActivityTag;

import java.util.List;

/**
 * 活动标签仓储接口
 *
 * 约定：
 * - Repository 内部使用 DO + MyBatis-Plus
 * - 对外只返回领域对象 ActivityTag
 */
public interface ActivityTagRepository {

    /**
     * 查询所有标签
     *
     * @param userId 用户ID，用于筛选
     * @return 标签列表（领域对象）
     */
    List<ActivityTag> findAll(Long userId);

    /**
     * 根据ID查询标签
     *
     * @param id 标签ID
     * @param userId 用户ID，用于权限校验
     * @return 标签（领域对象）
     */
    ActivityTag findById(Long id, Long userId);

    /**
     * 根据用户ID和名称查询标签
     *
     * @param userId 用户ID
     * @param name 标签名称
     * @return 标签（领域对象）
     */
    ActivityTag findByUserIdAndName(Long userId, String name);

    /**
     * 新增标签
     *
     * @param tag 新增的领域对象
     * @param userId 用户ID
     * @param creator 创建人ID
     * @return 影响的行数
     */
    int create(ActivityTag tag, Long userId, Long creator);

    /**
     * 更新标签
     *
     * @param tag 更新的领域对象
     * @param userId 用户ID，用于权限校验
     * @param modifier 修改人ID
     * @return 受影响行数
     */
    int update(ActivityTag tag, Long userId, Long modifier);

    /**
     * 删除标签（软删除）
     *
     * @param id 标签ID
     * @param userId 用户ID，用于权限校验
     * @param modifier 修改人ID
     * @return 受影响行数
     */
    int delete(Long id, Long userId, Long modifier);
}
