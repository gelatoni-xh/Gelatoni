package com.csxuhuan.gelatoni.application.assembler;

import com.csxuhuan.gelatoni.domain.model.entity.ActivityTag;
import com.csxuhuan.gelatoni.domain.query.ActivityTagCreateQuery;
import com.csxuhuan.gelatoni.domain.query.ActivityTagUpdateQuery;
import com.csxuhuan.gelatoni.application.dto.ActivityTagDTO;
import com.csxuhuan.gelatoni.interfaces.web.request.ActivityTagCreateRequest;
import com.csxuhuan.gelatoni.interfaces.web.request.ActivityTagUpdateRequest;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 活动标签 Assembler
 * 负责 Request/DTO 与 Domain 对象之间的转换
 */
public class ActivityTagAssembler {

    /**
     * 将前端创建请求转换为领域查询对象
     *
     * @param request 前端创建请求
     * @return 领域查询对象
     */
    public ActivityTagCreateQuery toDomainQuery(ActivityTagCreateRequest request) {
        return new ActivityTagCreateQuery(request.getName(), request.getColor());
    }

    /**
     * 将前端更新请求转换为领域查询对象
     *
     * @param request 前端更新请求
     * @return 领域查询对象
     */
    public ActivityTagUpdateQuery toDomainQuery(ActivityTagUpdateRequest request) {
        return new ActivityTagUpdateQuery(request.getId(), request.getName(), request.getColor());
    }

    /**
     * 将领域层实体转换为前端展示用 DTO
     *
     * @param tag 领域层实体
     * @return 前端展示用 DTO
     */
    public ActivityTagDTO toDTO(ActivityTag tag) {
        ActivityTagDTO dto = new ActivityTagDTO();
        dto.setId(tag.getId());
        dto.setName(tag.getName());
        dto.setColor(tag.getColor());
        dto.setCreateTime(tag.getCreateTime());
        return dto;
    }

    /**
     * 将领域层实体列表转换为前端展示用 DTO 列表
     *
     * @param tags 领域层实体列表
     * @return 前端展示用 DTO 列表
     */
    public List<ActivityTagDTO> toDTOList(List<ActivityTag> tags) {
        return tags.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
