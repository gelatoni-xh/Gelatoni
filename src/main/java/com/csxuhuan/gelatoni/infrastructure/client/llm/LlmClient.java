package com.csxuhuan.gelatoni.infrastructure.client.llm;

import java.util.List;

/**
 * LLM 客户端统一接口
 * <p>支持扩展不同的 LLM 提供商实现</p>
 */
public interface LlmClient {

    /**
     * 发送聊天请求
     *
     * @param messages 消息列表
     * @return 模型回复文本
     */
    String chat(List<ChatMessage> messages);
}
