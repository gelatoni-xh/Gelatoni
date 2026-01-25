package com.csxuhuan.gelatoni.domain.service;

import com.csxuhuan.gelatoni.domain.model.entity.ActivityTag;
import com.csxuhuan.gelatoni.domain.query.ActivityTagCreateQuery;
import com.csxuhuan.gelatoni.domain.query.ActivityTagUpdateQuery;

import java.util.List;

/**
 * 活动标签领域服务接口
 *
 * <p>定义标签相关的领域行为，标签用于对活动记录进行分类管理。
 *
 * @author csxuhuan
 */
public interface ActivityTagDomainService {

    /**
     * 查询所有标签
     *
     * <p>返回所有未删除的标签，按创建时间倒序排列。
     *
     * @param userId 用户ID，用于筛选
     * @return 标签列表
     */
    List<ActivityTag> findAll(Long userId);

    /**
     * 根据ID查询标签
     *
     * @param id 标签ID
     * @param userId 用户ID，用于权限校验
     * @return 标签
     */
    ActivityTag findById(Long id, Long userId);

    /**
     * 创建标签
     *
     * @param query 创建条件
     * @param userId 用户ID
     * @param creator 创建人ID
     * @return 影响的行数
     */
    int create(ActivityTagCreateQuery query, Long userId, Long creator);

    /**
     * 更新标签
     *
     * @param query 更新条件
     * @param userId 用户ID，用于权限校验
     * @param modifier 修改人ID
     * @return 影响的行数
     */
    int update(ActivityTagUpdateQuery query, Long userId, Long modifier);

    /**
     * 删除标签
     *
     * @param id 标签ID
     * @param userId 用户ID，用于权限校验
     * @param modifier 修改人ID
     * @return 影响的行数
     */
    int delete(Long id, Long userId, Long modifier);
}
