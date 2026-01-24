package com.csxuhuan.gelatoni.interfaces.web.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 创建TODO项请求
 */
public class TodoItemCreateRequest {

    /**
     * TODO 内容
     */
    @NotBlank(message = "TODO内容不能为空")
    @Size(max = 512, message = "TODO内容长度不能超过512个字符")
    private String content;

    /**
     * 标签ID（可选）
     */
    private Long tagId;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }
}
