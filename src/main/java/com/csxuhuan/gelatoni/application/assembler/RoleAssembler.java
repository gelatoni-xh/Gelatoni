package com.csxuhuan.gelatoni.application.assembler;

import com.csxuhuan.gelatoni.application.dto.RoleDTO;
import com.csxuhuan.gelatoni.domain.model.entity.Role;
import com.csxuhuan.gelatoni.domain.query.RoleCreateOrUpdateQuery;
import com.csxuhuan.gelatoni.interfaces.web.request.RoleCreateOrUpdateRequest;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色 Assembler
 * 负责 Request/DTO 与 Domain 对象之间的转换
 */
public class RoleAssembler {

    /**
     * 将前端请求转换为领域查询对象
     *
     * @param request 前端请求
     * @return 领域查询对象
     */
    public RoleCreateOrUpdateQuery toDomainQuery(RoleCreateOrUpdateRequest request) {
        return new RoleCreateOrUpdateQuery(
                request.getId(),
                request.getRoleCode(),
                request.getRoleName(),
                request.getStatus()
        );
    }

    /**
     * 将领域层实体转换为前端展示用 DTO
     *
     * @param role 领域层实体
     * @return 前端展示用 DTO
     */
    public RoleDTO toDTO(Role role) {
        if (role == null) {
            return null;
        }
        RoleDTO dto = new RoleDTO();
        dto.setId(role.getId());
        dto.setRoleCode(role.getRoleCode());
        dto.setRoleName(role.getRoleName());
        dto.setStatus(role.getStatus());
        dto.setCreateTime(role.getCreateTime());
        dto.setModifiedTime(role.getModifiedTime());
        return dto;
    }

    /**
     * 将领域层实体列表转换为前端展示用 DTO 列表
     *
     * @param roles 领域层实体列表
     * @return 前端展示用 DTO 列表
     */
    public List<RoleDTO> toDTOList(List<Role> roles) {
        return roles.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
