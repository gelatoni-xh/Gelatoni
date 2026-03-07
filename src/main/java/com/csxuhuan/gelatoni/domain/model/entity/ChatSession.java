package com.csxuhuan.gelatoni.domain.model.entity;

import java.time.LocalDateTime;

/**
 * 会话领域实体
 *
 * <p>代表一个用户的 AI 对话会话，session_id 由 userId:sessionUuid 组成，
 * 与 Redis 中的会话历史 key 保持一致。
 */
public class ChatSession {
    private final Long id;
    private final Long userId;
    /** 前端生成的 UUID，用于标识同一用户的不同会话 */
    private final String sessionUuid;
    /** 完整会话ID：userId:sessionUuid */
    private final String sessionId;
    /** 会话标题，取首条消息前 50 字 */
    private final String title;
    private final LocalDateTime createTime;
    private final LocalDateTime modifiedTime;
    private final Integer roundCount;

    public ChatSession(Long id, Long userId, String sessionUuid, String sessionId, String title, LocalDateTime createTime, LocalDateTime modifiedTime, Integer roundCount) {
        this.id = id;
        this.userId = userId;
        this.sessionUuid = sessionUuid;
        this.sessionId = sessionId;
        this.title = title;
        this.createTime = createTime;
        this.modifiedTime = modifiedTime;
        this.roundCount = roundCount;
    }

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public String getSessionUuid() { return sessionUuid; }
    public String getSessionId() { return sessionId; }
    public String getTitle() { return title; }
    public LocalDateTime getCreateTime() { return createTime; }
    public LocalDateTime getModifiedTime() { return modifiedTime; }
    public Integer getRoundCount() { return roundCount; }
}
