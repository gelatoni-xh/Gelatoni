package com.csxuhuan.gelatoni.infrastructure.repository.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

/**
 * chat_session 表的数据库映射对象
 *
 * <p>一个会话对应前端的一个独立对话窗口，session_id 唯一约束保证幂等写入。
 */
@TableName("chat_session")
public class ChatSessionDO {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    /** 前端生成的 UUID */
    private String sessionUuid;
    /** 完整会话ID：userId:sessionUuid，与 Redis key 保持一致 */
    private String sessionId;
    /** 会话标题，取首条消息前 50 字 */
    private String title;
    private Long creator;
    private Long modifier;
    private LocalDateTime createTime;
    private LocalDateTime modifiedTime;
    private Boolean isDeleted;
    private Integer roundCount;

    public ChatSessionDO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getSessionUuid() { return sessionUuid; }
    public void setSessionUuid(String sessionUuid) { this.sessionUuid = sessionUuid; }
    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public Long getCreator() { return creator; }
    public void setCreator(Long creator) { this.creator = creator; }
    public Long getModifier() { return modifier; }
    public void setModifier(Long modifier) { this.modifier = modifier; }
    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }
    public LocalDateTime getModifiedTime() { return modifiedTime; }
    public void setModifiedTime(LocalDateTime modifiedTime) { this.modifiedTime = modifiedTime; }
    public Boolean getIsDeleted() { return isDeleted; }
    public void setIsDeleted(Boolean isDeleted) { this.isDeleted = isDeleted; }
    public Integer getRoundCount() { return roundCount; }
    public void setRoundCount(Integer roundCount) { this.roundCount = roundCount; }
}
