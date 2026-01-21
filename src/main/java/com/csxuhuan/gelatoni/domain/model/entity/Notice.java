package com.csxuhuan.gelatoni.domain.model.entity;

import java.time.LocalDateTime;

/**
 * 公告领域对象
 *
 * 代表“一个有效的公告”
 * 不关心删除态、不关心数据库字段
 */
public class Notice {

    private final Long id;
    private final String title;
    private final String content;
    private final LocalDateTime createTime;
    private final LocalDateTime modifiedTime;

    public Notice(Long id,
                  String title,
                  String content,
                  LocalDateTime createTime,
                  LocalDateTime modifiedTime) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createTime = createTime;
        this.modifiedTime = modifiedTime;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public LocalDateTime getModifiedTime() {
        return modifiedTime;
    }
}
