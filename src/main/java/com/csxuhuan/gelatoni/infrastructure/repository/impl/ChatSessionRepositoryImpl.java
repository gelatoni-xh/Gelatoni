package com.csxuhuan.gelatoni.infrastructure.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csxuhuan.gelatoni.domain.model.converter.ChatSessionConverter;
import com.csxuhuan.gelatoni.domain.model.entity.ChatSession;
import com.csxuhuan.gelatoni.infrastructure.repository.ChatSessionRepository;
import com.csxuhuan.gelatoni.infrastructure.repository.entity.ChatSessionDO;
import com.csxuhuan.gelatoni.infrastructure.repository.mapper.ChatSessionMapper;
import org.springframework.stereotype.Repository;

/**
 * 会话仓储实现
 *
 * <p>使用 MyBatis-Plus 进行数据访问，saveIfAbsent 通过查询后插入实现幂等。
 */
@Repository
public class ChatSessionRepositoryImpl implements ChatSessionRepository {

    private final ChatSessionMapper chatSessionMapper;

    public ChatSessionRepositoryImpl(ChatSessionMapper chatSessionMapper) {
        this.chatSessionMapper = chatSessionMapper;
    }

    /**
     * {@inheritDoc}
     *
     * <p>先查询 session_id 是否存在，存在则跳过，避免重复创建会话记录。
     * title 超过 50 字时截断。
     */
    @Override
    public void saveIfAbsent(Long userId, String sessionUuid, String sessionId, String title) {
        LambdaQueryWrapper<ChatSessionDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ChatSessionDO::getSessionId, sessionId).eq(ChatSessionDO::getIsDeleted, false);
        if (chatSessionMapper.selectCount(wrapper) > 0) return;

        ChatSessionDO d = new ChatSessionDO();
        d.setUserId(userId);
        d.setSessionUuid(sessionUuid);
        d.setSessionId(sessionId);
        d.setTitle(title.length() > 50 ? title.substring(0, 50) : title);
        d.setCreator(userId);
        d.setModifier(userId);
        d.setIsDeleted(false);
        chatSessionMapper.insert(d);
    }

    @Override
    public IPage<ChatSession> pageByUserId(Long userId, int pageNo, int pageSize) {
        LambdaQueryWrapper<ChatSessionDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ChatSessionDO::getUserId, userId)
                .eq(ChatSessionDO::getIsDeleted, false)
                .orderByDesc(ChatSessionDO::getModifiedTime);
        IPage<ChatSessionDO> page = chatSessionMapper.selectPage(new Page<>(pageNo, pageSize), wrapper);
        return page.convert(ChatSessionConverter::toDomain);
    }
}
