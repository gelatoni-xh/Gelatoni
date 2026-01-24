package com.csxuhuan.gelatoni.application.assembler;

import com.csxuhuan.gelatoni.domain.model.entity.TodoTag;
import com.csxuhuan.gelatoni.domain.query.TodoTagCreateQuery;
import com.csxuhuan.gelatoni.application.dto.TodoTagDTO;
import com.csxuhuan.gelatoni.interfaces.web.request.TodoTagCreateRequest;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 标签 Assembler
 * 负责 Request/DTO 与 Domain 对象之间的转换
 */
public class TodoTagAssembler {

    /**
     * 将前端创建请求转换为领域查询对象
     *
     * @param request 前端创建请求
     * @return 领域查询对象
     */
    public TodoTagCreateQuery toDomainQuery(TodoTagCreateRequest request) {
        return new TodoTagCreateQuery(request.getName());
    }

    /**
     * 将领域层实体转换为前端展示用 DTO
     *
     * @param tag 领域层实体
     * @return 前端展示用 DTO
     */
    public TodoTagDTO toDTO(TodoTag tag) {
        TodoTagDTO dto = new TodoTagDTO();
        dto.setId(tag.getId());
        dto.setName(tag.getName());
        dto.setCreateTime(tag.getCreateTime());
        return dto;
    }

    /**
     * 将领域层实体列表转换为前端展示用 DTO 列表
     *
     * @param tags 领域层实体列表
     * @return 前端展示用 DTO 列表
     */
    public List<TodoTagDTO> toDTOList(List<TodoTag> tags) {
        return tags.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
