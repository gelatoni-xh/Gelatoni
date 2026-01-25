package com.csxuhuan.gelatoni.interfaces.web.request;

import javax.validation.constraints.NotNull;

/**
 * 删除请求（通用）
 */
public class ActivityDeleteRequest {

    /**
     * ID
     */
    @NotNull(message = "ID不能为空")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
