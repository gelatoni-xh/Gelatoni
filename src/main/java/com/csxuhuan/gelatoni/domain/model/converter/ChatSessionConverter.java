package com.csxuhuan.gelatoni.domain.model.converter;

import com.csxuhuan.gelatoni.domain.model.entity.ChatSession;
import com.csxuhuan.gelatoni.infrastructure.repository.entity.ChatSessionDO;

/**
 * ChatSession DO ↔ 领域实体转换器
 */
public class ChatSessionConverter {
    public static ChatSession toDomain(ChatSessionDO d) {
        if (d == null) return null;
        return new ChatSession(d.getId(), d.getUserId(), d.getSessionUuid(), d.getSessionId(), d.getTitle(), d.getCreateTime());
    }
}
