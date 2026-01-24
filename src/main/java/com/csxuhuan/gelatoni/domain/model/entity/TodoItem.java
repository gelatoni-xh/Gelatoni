package com.csxuhuan.gelatoni.domain.model.entity;

import java.time.LocalDateTime;

/**
 * TODO项领域对象
 *
 * 代表"一个有效的TODO项"
 * 不关心删除态、不关心数据库字段
 */
public class TodoItem {

    private final Long id;
    private final String content;
    private final Boolean completed;
    private final Long tagId;
    private final LocalDateTime createTime;
    private final LocalDateTime modifiedTime;

    public TodoItem(Long id, String content, Boolean completed, Long tagId,
                    LocalDateTime createTime, LocalDateTime modifiedTime) {
        this.id = id;
        this.content = content;
        this.completed = completed;
        this.tagId = tagId;
        this.createTime = createTime;
        this.modifiedTime = modifiedTime;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public Long getTagId() {
        return tagId;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public LocalDateTime getModifiedTime() {
        return modifiedTime;
    }
}
