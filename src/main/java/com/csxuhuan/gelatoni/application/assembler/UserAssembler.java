package com.csxuhuan.gelatoni.application.assembler;

import com.csxuhuan.gelatoni.domain.model.entity.User;
import com.csxuhuan.gelatoni.application.dto.UserDTO;
import com.csxuhuan.gelatoni.application.dto.UserInfoDTO;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * 用户 Assembler（装配器）
 *
 * <p>负责 Domain 对象与 DTO 之间的转换：
 * <ul>
 *     <li>Domain Entity → DTO：将领域实体转换为前端展示对象</li>
 * </ul>
 *
 * <p>设计说明：
 * Assembler 模式用于解耦各层之间的数据结构，避免领域对象直接暴露给前端，
 * 同时也避免前端数据结构影响领域模型的设计。
 *
 * @author csxuhuan
 */
@Component
public class UserAssembler {

    /**
     * 将领域层实体转换为前端展示用 DTO
     *
     * @param user 领域层实体
     * @return 前端展示用 DTO
     */
    public UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setNickname(user.getNickname());
        dto.setStatus(user.getStatus());
        dto.setCreateTime(user.getCreateTime());
        dto.setModifiedTime(user.getModifiedTime());
        return dto;
    }

    /**
     * 将用户实体、角色码列表、权限码列表转换为用户信息 DTO
     *
     * @param user            用户领域实体，可为 null
     * @param roleCodes       角色编码列表
     * @param permissionCodes 权限编码列表
     * @return 用户信息 DTO
     */
    public UserInfoDTO toUserInfoDTO(User user, List<String> roleCodes, List<String> permissionCodes) {
        UserDTO userDTO = toDTO(user);
        List<String> safeRoleCodes = roleCodes != null ? roleCodes : Collections.emptyList();
        List<String> safePermissionCodes = permissionCodes != null ? permissionCodes : Collections.emptyList();
        return new UserInfoDTO(userDTO, safeRoleCodes, safePermissionCodes);
    }
}
