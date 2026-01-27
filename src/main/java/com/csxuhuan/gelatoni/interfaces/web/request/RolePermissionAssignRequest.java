package com.csxuhuan.gelatoni.interfaces.web.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 角色分配权限请求
 */
public class RolePermissionAssignRequest {

    /** 角色ID */
    @NotNull(message = "角色ID不能为空")
    private Long roleId;

    /** 权限ID列表 */
    @NotEmpty(message = "权限ID列表不能为空")
    private List<Long> permissionIds;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public List<Long> getPermissionIds() {
        return permissionIds;
    }

    public void setPermissionIds(List<Long> permissionIds) {
        this.permissionIds = permissionIds;
    }
}
