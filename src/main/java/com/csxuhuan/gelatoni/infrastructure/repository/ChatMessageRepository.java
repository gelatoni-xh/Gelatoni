package com.csxuhuan.gelatoni.infrastructure.repository;

import com.csxuhuan.gelatoni.domain.model.entity.ChatMessage;
import java.util.List;

/**
 * 会话消息仓储接口
 *
 * <p>对外只暴露业务语义方法，屏蔽 MyBatis-Plus 实现细节。
 */
public interface ChatMessageRepository {
    /**
     * 保存一条问答记录
     *
     * @param sessionId   会话ID
     * @param userId      用户ID
     * @param message     用户消息
     * @param answer      AI 回答
     * @param intent      意图分类结果
     * @param intentModel 意图分类使用的模型
     * @param answerModel 实际回答使用的模型
     */
    void save(String sessionId, Long userId, String message, String answer, String intent, String intentModel, String answerModel);

    /**
     * 查询会话的所有消息，按创建时间倒序
     *
     * @param sessionId 会话ID
     * @return 消息列表
     */
    List<ChatMessage> findBySessionIdDesc(String sessionId);
}
