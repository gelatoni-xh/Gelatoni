package com.csxuhuan.gelatoni.domain.model.entity;

import java.time.LocalDateTime;

/**
 * 会话消息领域实体
 *
 * <p>记录一次完整的问答交互，包含用户消息、AI 回答，
 * 以及路由层的意图分类结果和实际使用的模型信息，用于成本分析和行为追踪。
 */
public class ChatMessage {
    private final Long id;
    private final String sessionId;
    private final Long userId;
    private final String message;
    private final String answer;
    /** 意图分类结果：chitchat / kb_query / complex */
    private final String intent;
    /** 意图分类使用的模型（base 层，如 minimax-m2.1） */
    private final String intentModel;
    /** 实际回答使用的模型（default/strong/cheap 层） */
    private final String answerModel;
    private final LocalDateTime createTime;

    public ChatMessage(Long id, String sessionId, Long userId, String message, String answer,
                       String intent, String intentModel, String answerModel, LocalDateTime createTime) {
        this.id = id;
        this.sessionId = sessionId;
        this.userId = userId;
        this.message = message;
        this.answer = answer;
        this.intent = intent;
        this.intentModel = intentModel;
        this.answerModel = answerModel;
        this.createTime = createTime;
    }

    public Long getId() { return id; }
    public String getSessionId() { return sessionId; }
    public Long getUserId() { return userId; }
    public String getMessage() { return message; }
    public String getAnswer() { return answer; }
    public String getIntent() { return intent; }
    public String getIntentModel() { return intentModel; }
    public String getAnswerModel() { return answerModel; }
    public LocalDateTime getCreateTime() { return createTime; }
}
