package com.csxuhuan.gelatoni.domain.query;

import com.csxuhuan.gelatoni.domain.model.entity.Role;

/**
 * 角色创建/更新查询对象
 */
public class RoleCreateOrUpdateQuery {

    /** 角色ID，更新时必填 */
    private Long id;

    /** 角色编码 */
    private String roleCode;

    /** 角色名称 */
    private String roleName;

    /** 状态：1-启用 0-禁用 */
    private Byte status;

    public RoleCreateOrUpdateQuery(Long id, String roleCode, String roleName, Byte status) {
        this.id = id;
        this.roleCode = roleCode;
        this.roleName = roleName;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 转换为角色领域实体
     */
    public Role toRole() {
        return new Role(id, roleCode, roleName, status, null, null, null, null);
    }
}
