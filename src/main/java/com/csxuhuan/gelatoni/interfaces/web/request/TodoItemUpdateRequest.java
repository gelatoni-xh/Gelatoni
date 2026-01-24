package com.csxuhuan.gelatoni.interfaces.web.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 更新TODO项请求
 */
public class TodoItemUpdateRequest {

    /**
     * TODO项ID
     */
    @NotNull(message = "ID不能为空")
    private Long id;

    /**
     * TODO 内容
     */
    @Size(max = 512, message = "TODO内容长度不能超过512个字符")
    private String content;

    /**
     * 是否完成
     */
    private Boolean completed;

    /**
     * 标签ID（可选）
     */
    private Long tagId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }
}
