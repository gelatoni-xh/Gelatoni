package com.csxuhuan.gelatoni.infrastructure.repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.csxuhuan.gelatoni.domain.model.entity.ChatSession;

/**
 * 会话仓储接口
 *
 * <p>对外只暴露业务语义方法，屏蔽 MyBatis-Plus 实现细节。
 */
public interface ChatSessionRepository {
    /**
     * 幂等写入会话记录，同一 sessionId 已存在则跳过
     *
     * @param userId      用户ID
     * @param sessionUuid 前端生成的会话UUID
     * @param sessionId   完整会话ID（userId:sessionUuid）
     * @param title       会话标题（取首条消息）
     */
    void saveIfAbsent(Long userId, String sessionUuid, String sessionId, String title);

    /**
     * 分页查询用户会话，按修改时间倒序
     *
     * @param userId   用户ID
     * @param pageNo   页码（从1开始）
     * @param pageSize 每页大小
     * @return 分页结果
     */
    IPage<ChatSession> pageByUserId(Long userId, int pageNo, int pageSize);
}
