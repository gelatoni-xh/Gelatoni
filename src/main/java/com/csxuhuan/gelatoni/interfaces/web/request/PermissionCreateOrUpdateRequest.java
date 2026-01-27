package com.csxuhuan.gelatoni.interfaces.web.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 创建或更新权限请求
 */
public class PermissionCreateOrUpdateRequest {

    /**
     * 权限ID（更新时必填）
     */
    private Long id;

    /**
     * 权限编码
     */
    @NotBlank(message = "权限编码不能为空")
    @Size(max = 64, message = "权限编码长度不能超过64个字符")
    private String permissionCode;

    /**
     * 权限名称
     */
    @NotBlank(message = "权限名称不能为空")
    @Size(max = 64, message = "权限名称长度不能超过64个字符")
    private String permissionName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPermissionCode() {
        return permissionCode;
    }

    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }
}