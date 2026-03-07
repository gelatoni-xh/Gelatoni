package com.csxuhuan.gelatoni.application.service.impl;

import com.csxuhuan.gelatoni.application.service.ChatHistoryAppService;
import com.csxuhuan.gelatoni.domain.service.ChatHistoryDomainService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 会话历史应用服务实现
 *
 * <p>通过 {@code @Async} 在独立线程池中执行持久化，避免阻塞 HTTP 响应。
 */
@Service
public class ChatHistoryAppServiceImpl implements ChatHistoryAppService {

    private final ChatHistoryDomainService chatHistoryDomainService;

    public ChatHistoryAppServiceImpl(ChatHistoryDomainService chatHistoryDomainService) {
        this.chatHistoryDomainService = chatHistoryDomainService;
    }

    /** {@inheritDoc} */
    @Override
    @Async
    public void saveChatAsync(Long userId, String sessionUuid, String sessionId, String message, String answer,
                               String intent, String intentModel, String answerModel) {
        chatHistoryDomainService.saveChat(userId, sessionUuid, sessionId, message, answer, intent, intentModel, answerModel);
    }
}
