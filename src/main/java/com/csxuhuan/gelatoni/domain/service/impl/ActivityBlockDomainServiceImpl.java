package com.csxuhuan.gelatoni.domain.service.impl;

import com.csxuhuan.gelatoni.application.exception.BizErrorCode;
import com.csxuhuan.gelatoni.application.exception.BizException;
import com.csxuhuan.gelatoni.domain.model.entity.ActivityBlock;
import com.csxuhuan.gelatoni.domain.model.entity.ActivityTag;
import com.csxuhuan.gelatoni.domain.query.ActivityBlockCreateOrUpdateQuery;
import com.csxuhuan.gelatoni.domain.service.ActivityBlockDomainService;
import com.csxuhuan.gelatoni.domain.service.ActivityTagDomainService;
import com.csxuhuan.gelatoni.infrastructure.repository.ActivityBlockRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * 活动记录领域服务实现
 *
 * <p>实现 {@link ActivityBlockDomainService} 接口，处理活动记录相关的领域逻辑。
 *
 * @author csxuhuan
 */
@Service
public class ActivityBlockDomainServiceImpl implements ActivityBlockDomainService {

    private final ActivityBlockRepository activityBlockRepository;
    private final ActivityTagDomainService activityTagDomainService;

    /**
     * 构造函数，注入仓储接口和依赖服务
     *
     * @param activityBlockRepository 活动记录仓储
     * @param activityTagDomainService 标签领域服务
     */
    public ActivityBlockDomainServiceImpl(ActivityBlockRepository activityBlockRepository,
                                         ActivityTagDomainService activityTagDomainService) {
        this.activityBlockRepository = activityBlockRepository;
        this.activityTagDomainService = activityTagDomainService;
    }

    @Override
    public List<ActivityBlock> findByDate(Long userId, LocalDate activityDate) {
        return activityBlockRepository.findByDate(userId, activityDate);
    }

    @Override
    public ActivityBlock findById(Long id, Long userId) {
        ActivityBlock block = activityBlockRepository.findById(id, userId);
        if (block == null) {
            throw new BizException(BizErrorCode.OPERATION_NOT_ALLOWED, "活动记录不存在或无权访问");
        }
        return block;
    }

    @Override
    public int createOrUpdate(ActivityBlockCreateOrUpdateQuery query, Long userId, Long modifier) {
        // 校验标签归属
        ActivityTag tag = activityTagDomainService.findById(query.getTagId(), userId);
        if (tag == null) {
            throw new BizException(BizErrorCode.OPERATION_NOT_ALLOWED, "标签不存在或无权访问");
        }

        // 校验时间合法性
        validateTime(query.getStartTime(), query.getEndTime());

        // 幂等性检查：根据 (user_id, date, startTime) 查找已存在的记录
        ActivityBlock existingBlock = activityBlockRepository.findByUserIdAndDateAndStartTime(
                userId, query.getActivityDate(), query.getStartTime());

        if (existingBlock != null) {
            // 存在则更新
            // 校验时间重叠（排除自身）
            validateNoOverlap(userId, query.getActivityDate(), query.getStartTime(), query.getEndTime(), existingBlock.getId());

            ActivityBlock block = query.toActivityBlock();
            // 设置ID以便更新
            ActivityBlock blockWithId = new ActivityBlock(
                    existingBlock.getId(),
                    block.getTagId(),
                    block.getActivityDate(),
                    block.getStartTime(),
                    block.getEndTime(),
                    block.getDetail(),
                    existingBlock.getCreateTime(),
                    null // modifiedTime 由数据库自动更新
            );
            return activityBlockRepository.update(blockWithId, userId, modifier);
        } else {
            // 不存在则创建
            // 校验时间重叠
            validateNoOverlap(userId, query.getActivityDate(), query.getStartTime(), query.getEndTime(), null);

            ActivityBlock block = query.toActivityBlock();
            return activityBlockRepository.create(block, userId, modifier);
        }
    }

    @Override
    public int delete(Long id, Long userId, Long modifier) {
        // 校验：活动记录存在且属于当前用户
        ActivityBlock block = activityBlockRepository.findById(id, userId);
        if (block == null) {
            throw new BizException(BizErrorCode.OPERATION_NOT_ALLOWED, "活动记录不存在或无权访问");
        }

        return activityBlockRepository.delete(id, userId, modifier);
    }

    /**
     * 校验时间合法性
     *
     * @param startTime 开始时间
     * @param endTime 结束时间
     */
    private void validateTime(LocalTime startTime, LocalTime endTime) {
        // 校验：startTime / endTime 只能是 xx:00 或 xx:30
        if (startTime.getMinute() != 0 && startTime.getMinute() != 30) {
            throw new BizException(BizErrorCode.INVALID_PARAM, "开始时间必须是整点或半点（00分或30分）");
        }
        if (endTime.getMinute() != 0 && endTime.getMinute() != 30) {
            throw new BizException(BizErrorCode.INVALID_PARAM, "结束时间必须是整点或半点（00分或30分）");
        }

        // 校验：endTime 必须大于 startTime
        if (!endTime.isAfter(startTime)) {
            throw new BizException(BizErrorCode.INVALID_PARAM, "结束时间必须大于开始时间");
        }

        // 校验：时间跨度必须是 30 分钟的整数倍
        long minutes = java.time.Duration.between(startTime, endTime).toMinutes();
        if (minutes % 30 != 0) {
            throw new BizException(BizErrorCode.INVALID_PARAM, "时间跨度必须是30分钟的整数倍");
        }
    }

    /**
     * 校验时间不重叠
     *
     * @param userId 用户ID
     * @param activityDate 活动日期
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param excludeId 排除的活动记录ID（用于更新时排除自身）
     */
    private void validateNoOverlap(Long userId, LocalDate activityDate, LocalTime startTime, LocalTime endTime, Long excludeId) {
        List<ActivityBlock> overlapping = activityBlockRepository.findOverlapping(
                userId, activityDate, startTime, endTime, excludeId);
        if (!overlapping.isEmpty()) {
            throw new BizException(BizErrorCode.BIZ_FAILED, "该时间段与其他活动记录重叠");
        }
    }
}
