package com.csxuhuan.gelatoni.interfaces.web.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 用户分配角色请求
 */
public class UserRoleAssignRequest {

    /** 用户ID */
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    /** 角色ID列表 */
    @NotEmpty(message = "角色ID列表不能为空")
    private List<Long> roleIds;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Long> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Long> roleIds) {
        this.roleIds = roleIds;
    }
}
