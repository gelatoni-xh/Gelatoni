package com.csxuhuan.gelatoni.infrastructure.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.csxuhuan.gelatoni.domain.model.common.DeletedEnum;
import com.csxuhuan.gelatoni.domain.model.converter.ActivityTagConverter;
import com.csxuhuan.gelatoni.domain.model.entity.ActivityTag;
import com.csxuhuan.gelatoni.infrastructure.repository.ActivityTagRepository;
import com.csxuhuan.gelatoni.infrastructure.repository.entity.ActivityTagDO;
import com.csxuhuan.gelatoni.infrastructure.repository.mapper.ActivityTagMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 活动标签仓储实现
 *
 * <p>实现 {@link ActivityTagRepository} 接口，使用 MyBatis-Plus 进行数据访问。
 *
 * @author csxuhuan
 */
@Repository
public class ActivityTagRepositoryImpl implements ActivityTagRepository {

    private final ActivityTagMapper activityTagMapper;

    /**
     * 构造函数，注入 Mapper
     *
     * @param activityTagMapper 标签 Mapper
     */
    public ActivityTagRepositoryImpl(ActivityTagMapper activityTagMapper) {
        this.activityTagMapper = activityTagMapper;
    }

    @Override
    public List<ActivityTag> findAll(Long userId) {
        LambdaQueryWrapper<ActivityTagDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ActivityTagDO::getIsDeleted, false)
                .eq(ActivityTagDO::getUserId, userId)
                .orderByDesc(ActivityTagDO::getCreateTime);

        List<ActivityTagDO> tagDOList = activityTagMapper.selectList(wrapper);

        return tagDOList.stream()
                .map(ActivityTagConverter::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public ActivityTag findById(Long id, Long userId) {
        LambdaQueryWrapper<ActivityTagDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ActivityTagDO::getId, id)
                .eq(ActivityTagDO::getIsDeleted, false)
                .eq(ActivityTagDO::getUserId, userId);

        ActivityTagDO tagDO = activityTagMapper.selectOne(wrapper);
        return ActivityTagConverter.toDomain(tagDO);
    }

    @Override
    public ActivityTag findByUserIdAndName(Long userId, String name) {
        LambdaQueryWrapper<ActivityTagDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ActivityTagDO::getUserId, userId)
                .eq(ActivityTagDO::getName, name)
                .eq(ActivityTagDO::getIsDeleted, false);

        ActivityTagDO tagDO = activityTagMapper.selectOne(wrapper);
        return ActivityTagConverter.toDomain(tagDO);
    }

    @Override
    public int create(ActivityTag tag, Long userId, Long creator) {
        ActivityTagDO tagDO = ActivityTagConverter.toDO(tag);
        tagDO.setUserId(userId);
        tagDO.setCreator(creator);
        tagDO.setModifier(creator);
        return activityTagMapper.insert(tagDO);
    }

    @Override
    public int update(ActivityTag tag, Long userId, Long modifier) {
        LambdaUpdateWrapper<ActivityTagDO> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(ActivityTagDO::getId, tag.getId())
                .eq(ActivityTagDO::getIsDeleted, false)
                .eq(ActivityTagDO::getUserId, userId);

        ActivityTagDO tagDO = new ActivityTagDO();
        tagDO.setName(tag.getName());
        tagDO.setColor(tag.getColor());
        tagDO.setModifier(modifier);

        return activityTagMapper.update(tagDO, wrapper);
    }

    @Override
    public int delete(Long id, Long userId, Long modifier) {
        LambdaUpdateWrapper<ActivityTagDO> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(ActivityTagDO::getId, id)
                .eq(ActivityTagDO::getIsDeleted, false)
                .eq(ActivityTagDO::getUserId, userId);

        ActivityTagDO tagDO = new ActivityTagDO();
        tagDO.setIsDeleted(DeletedEnum.DELETED.getValue());
        tagDO.setModifier(modifier);

        return activityTagMapper.update(tagDO, wrapper);
    }
}
