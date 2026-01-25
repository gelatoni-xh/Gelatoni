package com.csxuhuan.gelatoni.application.service;

import com.csxuhuan.gelatoni.domain.model.entity.ActivityBlock;
import com.csxuhuan.gelatoni.domain.model.entity.ActivityTag;
import com.csxuhuan.gelatoni.domain.query.ActivityBlockCreateOrUpdateQuery;
import com.csxuhuan.gelatoni.domain.query.ActivityTagCreateQuery;
import com.csxuhuan.gelatoni.domain.query.ActivityTagUpdateQuery;

import java.time.LocalDate;
import java.util.List;

/**
 * 活动记录应用服务接口
 *
 * <p>提供活动记录和标签相关的应用服务方法。
 *
 * @author csxuhuan
 */
public interface ActivityAppService {

    // ========== 活动标签相关方法 ==========

    /**
     * 查询所有标签
     *
     * <p>返回所有未删除的标签，按创建时间倒序排列。
     *
     * @param userId 用户ID，用于筛选
     * @return 标签列表
     */
    List<ActivityTag> findAllTags(Long userId);

    /**
     * 创建标签
     *
     * <p>创建一个新的活动标签。
     *
     * @param query 创建条件，包含 name 和 color
     * @param userId 用户ID
     * @return 影响的行数
     */
    int createTag(ActivityTagCreateQuery query, Long userId);

    /**
     * 更新标签
     *
     * <p>更新指定标签的信息，支持部分更新。
     *
     * @param query 更新条件，包含 id（必填）及可选的 name、color
     * @param userId 用户ID，用于权限校验
     * @return 影响的行数
     */
    int updateTag(ActivityTagUpdateQuery query, Long userId);

    /**
     * 删除标签
     *
     * <p>删除指定标签（软删除）。
     *
     * @param id 标签ID
     * @param userId 用户ID，用于权限校验
     * @return 影响的行数
     */
    int deleteTag(Long id, Long userId);

    // ========== 活动记录相关方法 ==========

    /**
     * 查询某一天的所有活动记录
     *
     * <p>返回指定日期的所有未删除的活动记录，按开始时间升序排列。
     *
     * @param userId 用户ID
     * @param activityDate 活动日期
     * @return 活动记录列表
     */
    List<ActivityBlock> findBlocksByDate(Long userId, LocalDate activityDate);

    /**
     * 创建或更新活动记录（幂等）
     *
     * <p>幂等规则：以 (user_id, date, startTime) 作为幂等键。
     * 如果存在则更新，不存在则创建。
     *
     * @param query 创建或更新条件
     * @param userId 用户ID
     * @return 影响的行数
     */
    int createOrUpdateBlock(ActivityBlockCreateOrUpdateQuery query, Long userId);

    /**
     * 删除活动记录
     *
     * <p>删除指定的活动记录（软删除）。
     *
     * @param id 活动记录ID
     * @param userId 用户ID，用于权限校验
     * @return 影响的行数
     */
    int deleteBlock(Long id, Long userId);
}
