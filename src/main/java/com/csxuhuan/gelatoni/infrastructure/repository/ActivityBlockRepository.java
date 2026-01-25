package com.csxuhuan.gelatoni.infrastructure.repository;

import com.csxuhuan.gelatoni.domain.model.entity.ActivityBlock;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * 活动记录仓储接口
 *
 * 约定：
 * - Repository 内部使用 DO + MyBatis-Plus
 * - 对外只返回领域对象 ActivityBlock
 */
public interface ActivityBlockRepository {

    /**
     * 查询某一天的所有活动记录
     *
     * @param userId 用户ID
     * @param activityDate 活动日期
     * @return 活动记录列表（领域对象）
     */
    List<ActivityBlock> findByDate(Long userId, LocalDate activityDate);

    /**
     * 根据ID查询活动记录
     *
     * @param id 活动记录ID
     * @param userId 用户ID，用于权限校验
     * @return 活动记录（领域对象）
     */
    ActivityBlock findById(Long id, Long userId);

    /**
     * 根据用户ID、日期和开始时间查询活动记录（用于幂等性检查）
     *
     * @param userId 用户ID
     * @param activityDate 活动日期
     * @param startTime 开始时间
     * @return 活动记录（领域对象）
     */
    ActivityBlock findByUserIdAndDateAndStartTime(Long userId, LocalDate activityDate, LocalTime startTime);

    /**
     * 查询指定日期和时间范围内是否有重叠的活动记录
     *
     * @param userId 用户ID
     * @param activityDate 活动日期
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param excludeId 排除的活动记录ID（用于更新时排除自身）
     * @return 重叠的活动记录列表
     */
    List<ActivityBlock> findOverlapping(Long userId, LocalDate activityDate, LocalTime startTime, LocalTime endTime, Long excludeId);

    /**
     * 新增活动记录
     *
     * @param block 新增的领域对象
     * @param userId 用户ID
     * @param creator 创建人ID
     * @return 影响的行数
     */
    int create(ActivityBlock block, Long userId, Long creator);

    /**
     * 更新活动记录
     *
     * @param block 更新的领域对象
     * @param userId 用户ID，用于权限校验
     * @param modifier 修改人ID
     * @return 受影响行数
     */
    int update(ActivityBlock block, Long userId, Long modifier);

    /**
     * 删除活动记录（软删除）
     *
     * @param id 活动记录ID
     * @param userId 用户ID，用于权限校验
     * @param modifier 修改人ID
     * @return 受影响行数
     */
    int delete(Long id, Long userId, Long modifier);
}
