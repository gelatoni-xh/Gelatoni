package com.csxuhuan.gelatoni.application.service;

/**
 * 会话历史应用服务接口
 *
 * <p>提供异步持久化入口，不阻塞 chat 接口的响应链路。
 */
public interface ChatHistoryAppService {
    /**
     * 异步保存一次问答记录
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
    void saveChatAsync(Long userId, String sessionUuid, String sessionId, String message, String answer,
                       String intent, String intentModel, String answerModel);
}
