package com.csxuhuan.gelatoni.domain.service;

import com.csxuhuan.gelatoni.domain.model.entity.ActivityBlock;
import com.csxuhuan.gelatoni.domain.query.ActivityBlockCreateOrUpdateQuery;

import java.time.LocalDate;
import java.util.List;

/**
 * 活动记录领域服务接口
 *
 * <p>定义活动记录相关的领域行为。
 *
 * @author csxuhuan
 */
public interface ActivityBlockDomainService {

    /**
     * 查询某一天的所有活动记录
     *
     * @param userId 用户ID
     * @param activityDate 活动日期
     * @return 活动记录列表
     */
    List<ActivityBlock> findByDate(Long userId, LocalDate activityDate);

    /**
     * 根据ID查询活动记录
     *
     * @param id 活动记录ID
     * @param userId 用户ID，用于权限校验
     * @return 活动记录
     */
    ActivityBlock findById(Long id, Long userId);

    /**
     * 创建或更新活动记录（幂等）
     *
     * <p>幂等规则：以 (user_id, date, startTime) 作为幂等键。
     * 如果存在则更新，不存在则创建。
     *
     * @param query 创建或更新条件
     * @param userId 用户ID
     * @param modifier 修改人ID（创建时作为创建人）
     * @return 影响的行数
     */
    int createOrUpdate(ActivityBlockCreateOrUpdateQuery query, Long userId, Long modifier);

    /**
     * 删除活动记录
     *
     * @param id 活动记录ID
     * @param userId 用户ID，用于权限校验
     * @param modifier 修改人ID
     * @return 影响的行数
     */
    int delete(Long id, Long userId, Long modifier);
}
