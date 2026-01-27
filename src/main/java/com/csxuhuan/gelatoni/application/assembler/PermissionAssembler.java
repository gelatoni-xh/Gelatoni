package com.csxuhuan.gelatoni.application.assembler;

import com.csxuhuan.gelatoni.application.dto.PermissionDTO;
import com.csxuhuan.gelatoni.domain.model.entity.Permission;
import com.csxuhuan.gelatoni.domain.query.PermissionCreateOrUpdateQuery;
import com.csxuhuan.gelatoni.interfaces.web.request.PermissionCreateOrUpdateRequest;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 权限 Assembler
 * 负责 Request/DTO 与 Domain 对象之间的转换
 */
public class PermissionAssembler {

    /**
     * 将前端请求转换为领域查询对象
     *
     * @param request 前端请求
     * @return 领域查询对象
     */
    public PermissionCreateOrUpdateQuery toDomainQuery(PermissionCreateOrUpdateRequest request) {
        return new PermissionCreateOrUpdateQuery(
                request.getId(),
                request.getPermissionCode(),
                request.getPermissionName()
        );
    }

    /**
     * 将领域层实体转换为前端展示用 DTO
     *
     * @param permission 领域层实体
     * @return 前端展示用 DTO
     */
    public PermissionDTO toDTO(Permission permission) {
        if (permission == null) {
            return null;
        }
        PermissionDTO dto = new PermissionDTO();
        dto.setId(permission.getId());
        dto.setPermissionCode(permission.getPermissionCode());
        dto.setPermissionName(permission.getPermissionName());
        dto.setCreateTime(permission.getCreateTime());
        dto.setModifiedTime(permission.getModifiedTime());
        return dto;
    }

    /**
     * 将领域层实体列表转换为前端展示用 DTO 列表
     *
     * @param permissions 领域层实体列表
     * @return 前端展示用 DTO 列表
     */
    public List<PermissionDTO> toDTOList(List<Permission> permissions) {
        return permissions.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}