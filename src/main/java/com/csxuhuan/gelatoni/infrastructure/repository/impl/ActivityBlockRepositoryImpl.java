package com.csxuhuan.gelatoni.infrastructure.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.csxuhuan.gelatoni.domain.model.common.DeletedEnum;
import com.csxuhuan.gelatoni.domain.model.converter.ActivityBlockConverter;
import com.csxuhuan.gelatoni.domain.model.entity.ActivityBlock;
import com.csxuhuan.gelatoni.infrastructure.repository.ActivityBlockRepository;
import com.csxuhuan.gelatoni.infrastructure.repository.entity.ActivityBlockDO;
import com.csxuhuan.gelatoni.infrastructure.repository.mapper.ActivityBlockMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 活动记录仓储实现
 *
 * <p>实现 {@link ActivityBlockRepository} 接口，使用 MyBatis-Plus 进行数据访问。
 *
 * @author csxuhuan
 */
@Repository
public class ActivityBlockRepositoryImpl implements ActivityBlockRepository {

    private final ActivityBlockMapper activityBlockMapper;

    /**
     * 构造函数，注入 Mapper
     *
     * @param activityBlockMapper 活动记录 Mapper
     */
    public ActivityBlockRepositoryImpl(ActivityBlockMapper activityBlockMapper) {
        this.activityBlockMapper = activityBlockMapper;
    }

    @Override
    public List<ActivityBlock> findByDate(Long userId, LocalDate activityDate) {
        LambdaQueryWrapper<ActivityBlockDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ActivityBlockDO::getIsDeleted, false)
                .eq(ActivityBlockDO::getUserId, userId)
                .eq(ActivityBlockDO::getActivityDate, activityDate)
                .orderByAsc(ActivityBlockDO::getStartTime);

        List<ActivityBlockDO> blockDOList = activityBlockMapper.selectList(wrapper);

        return blockDOList.stream()
                .map(ActivityBlockConverter::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public ActivityBlock findById(Long id, Long userId) {
        LambdaQueryWrapper<ActivityBlockDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ActivityBlockDO::getId, id)
                .eq(ActivityBlockDO::getIsDeleted, false)
                .eq(ActivityBlockDO::getUserId, userId);

        ActivityBlockDO blockDO = activityBlockMapper.selectOne(wrapper);
        return ActivityBlockConverter.toDomain(blockDO);
    }

    @Override
    public ActivityBlock findByUserIdAndDateAndStartTime(Long userId, LocalDate activityDate, LocalTime startTime) {
        LambdaQueryWrapper<ActivityBlockDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ActivityBlockDO::getUserId, userId)
                .eq(ActivityBlockDO::getActivityDate, activityDate)
                .eq(ActivityBlockDO::getStartTime, startTime)
                .eq(ActivityBlockDO::getIsDeleted, false);

        ActivityBlockDO blockDO = activityBlockMapper.selectOne(wrapper);
        return ActivityBlockConverter.toDomain(blockDO);
    }

    @Override
    public List<ActivityBlock> findOverlapping(Long userId, LocalDate activityDate, LocalTime startTime, LocalTime endTime, Long excludeId) {
        LambdaQueryWrapper<ActivityBlockDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ActivityBlockDO::getIsDeleted, false)
                .eq(ActivityBlockDO::getUserId, userId)
                .eq(ActivityBlockDO::getActivityDate, activityDate)
                // 重叠条件：(startTime < endTime) AND (endTime > startTime)
                .and(w -> w.and(w1 -> w1.lt(ActivityBlockDO::getStartTime, endTime)
                        .gt(ActivityBlockDO::getEndTime, startTime)));

        if (excludeId != null) {
            wrapper.ne(ActivityBlockDO::getId, excludeId);
        }

        List<ActivityBlockDO> blockDOList = activityBlockMapper.selectList(wrapper);

        return blockDOList.stream()
                .map(ActivityBlockConverter::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public int create(ActivityBlock block, Long userId, Long creator) {
        ActivityBlockDO blockDO = ActivityBlockConverter.toDO(block);
        blockDO.setUserId(userId);
        blockDO.setCreator(creator);
        blockDO.setModifier(creator);
        return activityBlockMapper.insert(blockDO);
    }

    @Override
    public int update(ActivityBlock block, Long userId, Long modifier) {
        LambdaUpdateWrapper<ActivityBlockDO> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(ActivityBlockDO::getId, block.getId())
                .eq(ActivityBlockDO::getIsDeleted, false)
                .eq(ActivityBlockDO::getUserId, userId);

        ActivityBlockDO blockDO = new ActivityBlockDO();
        blockDO.setTagId(block.getTagId());
        blockDO.setActivityDate(block.getActivityDate());
        blockDO.setStartTime(block.getStartTime());
        blockDO.setEndTime(block.getEndTime());
        blockDO.setDetail(block.getDetail());
        blockDO.setModifier(modifier);

        return activityBlockMapper.update(blockDO, wrapper);
    }

    @Override
    public int delete(Long id, Long userId, Long modifier) {
        LambdaUpdateWrapper<ActivityBlockDO> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(ActivityBlockDO::getId, id)
                .eq(ActivityBlockDO::getIsDeleted, false)
                .eq(ActivityBlockDO::getUserId, userId);

        ActivityBlockDO blockDO = new ActivityBlockDO();
        blockDO.setIsDeleted(DeletedEnum.DELETED.getValue());
        blockDO.setModifier(modifier);

        return activityBlockMapper.update(blockDO, wrapper);
    }
}
