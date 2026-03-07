package com.csxuhuan.gelatoni.domain.model.converter;

import com.csxuhuan.gelatoni.domain.model.entity.ChatMessage;
import com.csxuhuan.gelatoni.infrastructure.repository.entity.ChatMessageDO;

/**
 * ChatMessage DO ↔ 领域实体转换器
 */
public class ChatMessageConverter {
    public static ChatMessage toDomain(ChatMessageDO d) {
        if (d == null) return null;
        return new ChatMessage(d.getId(), d.getSessionId(), d.getUserId(), d.getMessage(), d.getAnswer(),
                d.getIntent(), d.getIntentModel(), d.getAnswerModel(), d.getCreateTime());
    }
}
