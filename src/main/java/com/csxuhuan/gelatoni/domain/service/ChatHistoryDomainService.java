package com.csxuhuan.gelatoni.domain.service;

/**
 * 会话历史领域服务接口
 *
 * <p>封装会话和消息的持久化逻辑，协调 ChatSessionRepository 和 ChatMessageRepository。
 */
public interface ChatHistoryDomainService {
    /**
     * 保存一次完整的问答记录
     *
     * <p>先幂等写入会话，再写入消息。
     *
     * @param userId      用户ID
     * @param sessionUuid 前端会话UUID
     * @param sessionId   完整会话ID
     * @param message     用户消息
     * @param answer      AI 回答
     * @param intent      意图分类结果
     * @param intentModel 意图分类使用的模型
     * @param answerModel 实际回答使用的模型
     */
    void saveChat(Long userId, String sessionUuid, String sessionId, String message, String answer,
                  String intent, String intentModel, String answerModel);
}
