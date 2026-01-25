package com.csxuhuan.gelatoni.domain.service.impl;

import com.csxuhuan.gelatoni.application.exception.BizErrorCode;
import com.csxuhuan.gelatoni.application.exception.BizException;
import com.csxuhuan.gelatoni.domain.model.entity.ActivityTag;
import com.csxuhuan.gelatoni.domain.query.ActivityTagCreateQuery;
import com.csxuhuan.gelatoni.domain.query.ActivityTagUpdateQuery;
import com.csxuhuan.gelatoni.domain.service.ActivityTagDomainService;
import com.csxuhuan.gelatoni.infrastructure.repository.ActivityTagRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 活动标签领域服务实现
 *
 * <p>实现 {@link ActivityTagDomainService} 接口，处理标签相关的领域逻辑。
 *
 * @author csxuhuan
 */
@Service
public class ActivityTagDomainServiceImpl implements ActivityTagDomainService {

    private final ActivityTagRepository activityTagRepository;

    /**
     * 构造函数，注入仓储接口
     *
     * @param activityTagRepository 标签仓储
     */
    public ActivityTagDomainServiceImpl(ActivityTagRepository activityTagRepository) {
        this.activityTagRepository = activityTagRepository;
    }

    @Override
    public List<ActivityTag> findAll(Long userId) {
        return activityTagRepository.findAll(userId);
    }

    @Override
    public ActivityTag findById(Long id, Long userId) {
        ActivityTag tag = activityTagRepository.findById(id, userId);
        if (tag == null) {
            throw new BizException(BizErrorCode.OPERATION_NOT_ALLOWED, "标签不存在或无权访问");
        }
        return tag;
    }

    @Override
    public int create(ActivityTagCreateQuery query, Long userId, Long creator) {
        // 校验：user + name 唯一
        ActivityTag existingTag = activityTagRepository.findByUserIdAndName(userId, query.getName());
        if (existingTag != null) {
            throw new BizException(BizErrorCode.BIZ_FAILED, "标签名称已存在");
        }

        ActivityTag tag = query.toActivityTag();
        return activityTagRepository.create(tag, userId, creator);
    }

    @Override
    public int update(ActivityTagUpdateQuery query, Long userId, Long modifier) {
        // 校验：标签存在且属于当前用户
        ActivityTag existingTag = activityTagRepository.findById(query.getId(), userId);
        if (existingTag == null) {
            throw new BizException(BizErrorCode.OPERATION_NOT_ALLOWED, "标签不存在或无权访问");
        }

        // 如果更新名称，需要校验唯一性
        if (query.getName() != null && !query.getName().equals(existingTag.getName())) {
            ActivityTag duplicateTag = activityTagRepository.findByUserIdAndName(userId, query.getName());
            if (duplicateTag != null) {
                throw new BizException(BizErrorCode.BIZ_FAILED, "标签名称已存在");
            }
        }

        ActivityTag tag = query.toActivityTag();
        return activityTagRepository.update(tag, userId, modifier);
    }

    @Override
    public int delete(Long id, Long userId, Long modifier) {
        // 校验：标签存在且属于当前用户
        ActivityTag tag = activityTagRepository.findById(id, userId);
        if (tag == null) {
            throw new BizException(BizErrorCode.OPERATION_NOT_ALLOWED, "标签不存在或无权访问");
        }

        return activityTagRepository.delete(id, userId, modifier);
    }
}
