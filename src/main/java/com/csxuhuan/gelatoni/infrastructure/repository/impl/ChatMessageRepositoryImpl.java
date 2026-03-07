package com.csxuhuan.gelatoni.infrastructure.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.csxuhuan.gelatoni.domain.model.converter.ChatMessageConverter;
import com.csxuhuan.gelatoni.domain.model.entity.ChatMessage;
import com.csxuhuan.gelatoni.infrastructure.repository.ChatMessageRepository;
import com.csxuhuan.gelatoni.infrastructure.repository.entity.ChatMessageDO;
import com.csxuhuan.gelatoni.infrastructure.repository.mapper.ChatMessageMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * 会话消息仓储实现
 *
 * <p>使用 MyBatis-Plus 进行数据访问，每次问答完成后写入一条记录。
 */
@Repository
public class ChatMessageRepositoryImpl implements ChatMessageRepository {

    private final ChatMessageMapper chatMessageMapper;

    public ChatMessageRepositoryImpl(ChatMessageMapper chatMessageMapper) {
        this.chatMessageMapper = chatMessageMapper;
    }

    /** {@inheritDoc} */
    @Override
    public void save(String sessionId, Long userId, String message, String answer, String intent, String intentModel, String answerModel) {
        ChatMessageDO d = new ChatMessageDO();
        d.setSessionId(sessionId);
        d.setUserId(userId);
        d.setMessage(message);
        d.setAnswer(answer);
        d.setIntent(intent);
        d.setIntentModel(intentModel);
        d.setAnswerModel(answerModel);
        d.setCreator(userId);
        d.setModifier(userId);
        d.setIsDeleted(false);
        chatMessageMapper.insert(d);
    }

    @Override
    public List<ChatMessage> findBySessionIdDesc(String sessionId) {
        LambdaQueryWrapper<ChatMessageDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ChatMessageDO::getSessionId, sessionId)
                .eq(ChatMessageDO::getIsDeleted, false)
                .orderByDesc(ChatMessageDO::getCreateTime);
        return chatMessageMapper.selectList(wrapper).stream()
                .map(ChatMessageConverter::toDomain)
                .collect(java.util.stream.Collectors.toList());
    }
}
