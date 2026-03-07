package com.csxuhuan.gelatoni.infrastructure.repository.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

/**
 * chat_message 表的数据库映射对象
 *
 * <p>除基础问答字段外，额外记录路由层的意图分类和模型选择信息，
 * 便于后续成本分析和模型效果评估。
 */
@TableName("chat_message")
public class ChatMessageDO {
    @TableId(type = IdType.AUTO)
    private Long id;
    /** 关联会话ID（userId:sessionUuid） */
    private String sessionId;
    private Long userId;
    private String message;
    private String answer;
    /** 意图分类结果：chitchat / kb_query / complex */
    private String intent;
    /** 意图分类使用的模型（base 层） */
    private String intentModel;
    /** 实际回答使用的模型 */
    private String answerModel;
    private Long creator;
    private Long modifier;
    private LocalDateTime createTime;
    private LocalDateTime modifiedTime;
    private Boolean isDeleted;

    public ChatMessageDO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getAnswer() { return answer; }
    public void setAnswer(String answer) { this.answer = answer; }
    public String getIntent() { return intent; }
    public void setIntent(String intent) { this.intent = intent; }
    public String getIntentModel() { return intentModel; }
    public void setIntentModel(String intentModel) { this.intentModel = intentModel; }
    public String getAnswerModel() { return answerModel; }
    public void setAnswerModel(String answerModel) { this.answerModel = answerModel; }
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
}
