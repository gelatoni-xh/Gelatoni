package com.csxuhuan.gelatoni.application.assembler;

import com.csxuhuan.gelatoni.domain.model.entity.ActivityBlock;
import com.csxuhuan.gelatoni.domain.query.ActivityBlockCreateOrUpdateQuery;
import com.csxuhuan.gelatoni.application.dto.ActivityBlockDTO;
import com.csxuhuan.gelatoni.interfaces.web.request.ActivityBlockCreateOrUpdateRequest;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 活动记录 Assembler
 * 负责 Request/DTO 与 Domain 对象之间的转换
 */
public class ActivityBlockAssembler {

    /**
     * 将前端创建或更新请求转换为领域查询对象
     *
     * @param request 前端创建或更新请求
     * @return 领域查询对象
     */
    public ActivityBlockCreateOrUpdateQuery toDomainQuery(ActivityBlockCreateOrUpdateRequest request) {
        return new ActivityBlockCreateOrUpdateQuery(
                request.getTagId(),
                request.getActivityDate(),
                request.getStartTime(),
                request.getEndTime(),
                request.getDetail()
        );
    }

    /**
     * 将领域层实体转换为前端展示用 DTO
     *
     * @param block 领域层实体
     * @return 前端展示用 DTO
     */
    public ActivityBlockDTO toDTO(ActivityBlock block) {
        ActivityBlockDTO dto = new ActivityBlockDTO();
        dto.setId(block.getId());
        dto.setTagId(block.getTagId());
        dto.setActivityDate(block.getActivityDate());
        dto.setStartTime(block.getStartTime());
        dto.setEndTime(block.getEndTime());
        dto.setDetail(block.getDetail());
        dto.setCreateTime(block.getCreateTime());
        return dto;
    }

    /**
     * 将领域层实体列表转换为前端展示用 DTO 列表
     *
     * @param blocks 领域层实体列表
     * @return 前端展示用 DTO 列表
     */
    public List<ActivityBlockDTO> toDTOList(List<ActivityBlock> blocks) {
        return blocks.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
