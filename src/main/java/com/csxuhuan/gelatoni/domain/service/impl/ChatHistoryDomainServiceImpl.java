package com.csxuhuan.gelatoni.domain.service.impl;

import com.csxuhuan.gelatoni.domain.service.ChatHistoryDomainService;
import com.csxuhuan.gelatoni.infrastructure.repository.ChatMessageRepository;
import com.csxuhuan.gelatoni.infrastructure.repository.ChatSessionRepository;
import org.springframework.stereotype.Service;

/**
 * 会话历史领域服务实现
 *
 * <p>先幂等写入会话记录，再写入消息记录，两步操作均委托给对应 Repository。
 */
@Service
public class ChatHistoryDomainServiceImpl implements ChatHistoryDomainService {

    private final ChatSessionRepository chatSessionRepository;
    private final ChatMessageRepository chatMessageRepository;

    public ChatHistoryDomainServiceImpl(ChatSessionRepository chatSessionRepository,
                                        ChatMessageRepository chatMessageRepository) {
        this.chatSessionRepository = chatSessionRepository;
        this.chatMessageRepository = chatMessageRepository;
    }

    /** {@inheritDoc} */
    @Override
    public void saveChat(Long userId, String sessionUuid, String sessionId, String message, String answer,
                         String intent, String intentModel, String answerModel) {
        chatSessionRepository.saveIfAbsent(userId, sessionUuid, sessionId, message);
        chatSessionRepository.incrementRoundCount(sessionId);
        chatMessageRepository.save(sessionId, userId, message, answer, intent, intentModel, answerModel);
    }
}
