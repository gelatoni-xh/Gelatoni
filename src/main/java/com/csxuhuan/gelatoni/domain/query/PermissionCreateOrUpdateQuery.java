package com.csxuhuan.gelatoni.domain.query;

import com.csxuhuan.gelatoni.domain.model.entity.Permission;

/**
 * 权限创建/更新查询对象
 */
public class PermissionCreateOrUpdateQuery {

    /** 权限ID，更新时必填 */
    private Long id;

    /** 权限编码 */
    private String permissionCode;

    /** 权限名称 */
    private String permissionName;

    public PermissionCreateOrUpdateQuery(Long id, String permissionCode, String permissionName) {
        this.id = id;
        this.permissionCode = permissionCode;
        this.permissionName = permissionName;
    }

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

    /**
     * 转换为权限领域实体
     */
    public Permission toPermission() {
        return new Permission(id, permissionCode, permissionName, null, null, null, null);
    }
}