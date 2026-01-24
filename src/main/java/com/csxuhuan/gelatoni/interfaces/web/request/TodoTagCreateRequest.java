package com.csxuhuan.gelatoni.interfaces.web.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 创建标签请求
 */
public class TodoTagCreateRequest {

    /**
     * 标签名称
     */
    @NotBlank(message = "标签名称不能为空")
    @Size(max = 64, message = "标签名称长度不能超过64个字符")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
