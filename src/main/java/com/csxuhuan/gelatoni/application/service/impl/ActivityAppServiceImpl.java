package com.csxuhuan.gelatoni.application.service.impl;

import com.csxuhuan.gelatoni.application.service.ActivityAppService;
import com.csxuhuan.gelatoni.domain.model.entity.ActivityBlock;
import com.csxuhuan.gelatoni.domain.model.entity.ActivityTag;
import com.csxuhuan.gelatoni.domain.query.ActivityBlockCreateOrUpdateQuery;
import com.csxuhuan.gelatoni.domain.query.ActivityTagCreateQuery;
import com.csxuhuan.gelatoni.domain.query.ActivityTagUpdateQuery;
import com.csxuhuan.gelatoni.domain.service.ActivityBlockDomainService;
import com.csxuhuan.gelatoni.domain.service.ActivityTagDomainService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * 活动记录应用服务实现
 *
 * <p>实现 {@link ActivityAppService} 接口，协调领域服务完成业务逻辑。
 *
 * @author csxuhuan
 */
@Service
public class ActivityAppServiceImpl implements ActivityAppService {

    private final ActivityTagDomainService activityTagDomainService;
    private final ActivityBlockDomainService activityBlockDomainService;

    /**
     * 构造函数，注入领域服务
     *
     * @param activityTagDomainService 活动标签领域服务
     * @param activityBlockDomainService 活动记录领域服务
     */
    public ActivityAppServiceImpl(ActivityTagDomainService activityTagDomainService,
                                 ActivityBlockDomainService activityBlockDomainService) {
        this.activityTagDomainService = activityTagDomainService;
        this.activityBlockDomainService = activityBlockDomainService;
    }

    // ========== 活动标签相关方法 ==========

    @Override
    public List<ActivityTag> findAllTags(Long userId) {
        return activityTagDomainService.findAll(userId);
    }

    @Override
    public int createTag(ActivityTagCreateQuery query, Long userId) {
        return activityTagDomainService.create(query, userId, userId);
    }

    @Override
    public int updateTag(ActivityTagUpdateQuery query, Long userId) {
        return activityTagDomainService.update(query, userId, userId);
    }

    @Override
    public int deleteTag(Long id, Long userId) {
        return activityTagDomainService.delete(id, userId, userId);
    }

    // ========== 活动记录相关方法 ==========

    @Override
    public List<ActivityBlock> findBlocksByDate(Long userId, LocalDate activityDate) {
        return activityBlockDomainService.findByDate(userId, activityDate);
    }

    @Override
    public int createOrUpdateBlock(ActivityBlockCreateOrUpdateQuery query, Long userId) {
        return activityBlockDomainService.createOrUpdate(query, userId, userId);
    }

    @Override
    public int deleteBlock(Long id, Long userId) {
        return activityBlockDomainService.delete(id, userId, userId);
    }
}
