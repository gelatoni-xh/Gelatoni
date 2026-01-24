package com.csxuhuan.gelatoni.interfaces.web.assembler;

import com.csxuhuan.gelatoni.domain.model.entity.TodoItem;
import com.csxuhuan.gelatoni.domain.model.entity.TodoTag;
import com.csxuhuan.gelatoni.domain.query.TodoItemCreateQuery;
import com.csxuhuan.gelatoni.domain.query.TodoItemUpdateQuery;
import com.csxuhuan.gelatoni.interfaces.web.dto.TodoItemDTO;
import com.csxuhuan.gelatoni.interfaces.web.request.TodoItemCreateRequest;
import com.csxuhuan.gelatoni.interfaces.web.request.TodoItemUpdateRequest;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * TODO项 Assembler
 * 负责 Request/DTO 与 Domain 对象之间的转换
 */
public class TodoItemAssembler {

    /**
     * 将前端创建请求转换为领域查询对象
     *
     * @param request 前端创建请求
     * @return 领域查询对象
     */
    public TodoItemCreateQuery toDomainQuery(TodoItemCreateRequest request) {
        return new TodoItemCreateQuery(request.getContent(), request.getTagId());
    }

    /**
     * 将前端更新请求转换为领域查询对象
     *
     * @param request 前端更新请求
     * @return 领域查询对象
     */
    public TodoItemUpdateQuery toDomainQuery(TodoItemUpdateRequest request) {
        return new TodoItemUpdateQuery(
                request.getId(),
                request.getContent(),
                request.getCompleted(),
                request.getTagId()
        );
    }

    /**
     * 将领域层实体转换为前端展示用 DTO
     *
     * @param item 领域层实体
     * @return 前端展示用 DTO
     */
    public TodoItemDTO toDTO(TodoItem item) {
        TodoItemDTO dto = new TodoItemDTO();
        dto.setId(item.getId());
        dto.setContent(item.getContent());
        dto.setCompleted(item.getCompleted());
        dto.setTagId(item.getTagId());
        dto.setCreateTime(item.getCreateTime());
        dto.setModifiedTime(item.getModifiedTime());
        return dto;
    }

    /**
     * 将领域层实体转换为前端展示用 DTO，并填充标签名称
     *
     * @param item    领域层实体
     * @param tagMap  标签ID到标签的映射
     * @return 前端展示用 DTO
     */
    public TodoItemDTO toDTO(TodoItem item, Map<Long, TodoTag> tagMap) {
        TodoItemDTO dto = toDTO(item);
        if (item.getTagId() != null && tagMap.containsKey(item.getTagId())) {
            dto.setTagName(tagMap.get(item.getTagId()).getName());
        }
        return dto;
    }

    /**
     * 将领域层实体列表转换为前端展示用 DTO 列表
     *
     * @param items 领域层实体列表
     * @return 前端展示用 DTO 列表
     */
    public List<TodoItemDTO> toDTOList(List<TodoItem> items) {
        return items.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * 将领域层实体列表转换为前端展示用 DTO 列表，并填充标签名称
     *
     * @param items  领域层实体列表
     * @param tagMap 标签ID到标签的映射
     * @return 前端展示用 DTO 列表
     */
    public List<TodoItemDTO> toDTOList(List<TodoItem> items, Map<Long, TodoTag> tagMap) {
        return items.stream()
                .map(item -> toDTO(item, tagMap))
                .collect(Collectors.toList());
    }
}
