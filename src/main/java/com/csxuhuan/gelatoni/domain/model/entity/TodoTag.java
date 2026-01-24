package com.csxuhuan.gelatoni.domain.model.entity;

import java.time.LocalDateTime;

/**
 * 标签领域对象
 *
 * 代表"一个有效的标签"
 * 不关心删除态、不关心数据库字段
 */
public class TodoTag {

    private final Long id;
    private final String name;
    private final LocalDateTime createTime;
    private final LocalDateTime modifiedTime;

    public TodoTag(Long id, String name, LocalDateTime createTime, LocalDateTime modifiedTime) {
        this.id = id;
        this.name = name;
        this.createTime = createTime;
        this.modifiedTime = modifiedTime;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public LocalDateTime getModifiedTime() {
        return modifiedTime;
    }
}
