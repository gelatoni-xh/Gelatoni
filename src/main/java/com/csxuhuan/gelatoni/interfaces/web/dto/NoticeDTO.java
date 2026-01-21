package com.csxuhuan.gelatoni.interfaces.web.dto;

import java.time.LocalDateTime;

/**
 * 公告前端展示 DTO
 */
public class NoticeDTO {

    private String title;

    private String content;

    private LocalDateTime createTime;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}
