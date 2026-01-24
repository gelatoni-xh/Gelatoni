package com.csxuhuan.gelatoni.application.dto;

import java.util.List;

/**
 * 用户信息数据传输对象（DTO）
 *
 * <p>用于向前端返回用户完整信息，包括：
 * <ul>
 *     <li>用户基本信息</li>
 *     <li>用户拥有的所有角色编码列表</li>
 *     <li>用户拥有的所有权限编码列表</li>
 * </ul>
 *
 * <p>字段说明：
 * <ul>
 *     <li>user - 用户基本信息 DTO</li>
 *     <li>roleCodes - 角色编码列表</li>
 *     <li>permissionCodes - 权限编码列表</li>
 * </ul>
 *
 * @author csxuhuan
 */
public class UserInfoDTO {

    /** 用户基本信息 */
    private UserDTO user;

    /** 角色编码列表 */
    private List<String> roleCodes;

    /** 权限编码列表 */
    private List<String> permissionCodes;

    public UserInfoDTO() {
    }

    public UserInfoDTO(UserDTO user, List<String> roleCodes, List<String> permissionCodes) {
        this.user = user;
        this.roleCodes = roleCodes;
        this.permissionCodes = permissionCodes;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public List<String> getRoleCodes() {
        return roleCodes;
    }

    public void setRoleCodes(List<String> roleCodes) {
        this.roleCodes = roleCodes;
    }

    public List<String> getPermissionCodes() {
        return permissionCodes;
    }

    public void setPermissionCodes(List<String> permissionCodes) {
        this.permissionCodes = permissionCodes;
    }
}
