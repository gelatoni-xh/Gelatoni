package com.csxuhuan.gelatoni.interfaces.web.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 更新活动标签请求
 */
public class ActivityTagUpdateRequest {

    /**
     * 标签ID
     */
    @NotNull(message = "ID不能为空")
    private Long id;

    /**
     * 标签名称
     */
    @Size(max = 64, message = "标签名称长度不能超过64个字符")
    private String name;

    /**
     * 标签颜色，如 #FFAA00
     */
    @Pattern(regexp = "^#[0-9A-Fa-f]{6}$", message = "标签颜色格式不正确，应为#开头的6位十六进制颜色值")
    private String color;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
