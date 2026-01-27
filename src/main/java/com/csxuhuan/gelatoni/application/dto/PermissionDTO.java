package com.csxuhuan.gelatoni.application.dto;

import java.time.LocalDateTime;

/**
 * 权限数据传输对象（DTO）
 *
 * <p>用于向前端返回权限信息。
 */
public class PermissionDTO {

    /** 权限唯一标识 */
    private Long id;

    /** 权限编码 */
    private String permissionCode;

    /** 权限名称 */
    private String permissionName;

    /** 权限类型：1-菜单 2-按钮 3-接口 */
    private Byte type;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 最后修改时间 */
    private LocalDateTime modifiedTime;

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

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(LocalDateTime modifiedTime) {
        this.modifiedTime = modifiedTime;
    }
}
