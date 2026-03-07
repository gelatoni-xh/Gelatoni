package com.csxuhuan.gelatoni.application.dto;

import java.time.LocalDateTime;

public class ChatSessionDTO {

    private String sessionUuid;
    private String title;
    private LocalDateTime createTime;
    private LocalDateTime modifiedTime;
    private Integer roundCount;

    public String getSessionUuid() {
        return sessionUuid;
    }

    public void setSessionUuid(String sessionUuid) {
        this.sessionUuid = sessionUuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(LocalDateTime modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public Integer getRoundCount() {
        return roundCount;
    }

    public void setRoundCount(Integer roundCount) {
        this.roundCount = roundCount;
    }
}
