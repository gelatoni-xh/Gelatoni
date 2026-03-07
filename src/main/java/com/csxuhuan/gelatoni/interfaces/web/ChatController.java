package com.csxuhuan.gelatoni.interfaces.web;

import com.csxuhuan.gelatoni.application.service.ChatHistoryAppService;
import com.csxuhuan.gelatoni.interfaces.config.AuthCheck;
import com.csxuhuan.gelatoni.interfaces.web.common.BaseResponse;
import com.csxuhuan.gelatoni.interfaces.web.common.PermissionConstants;
import com.csxuhuan.gelatoni.interfaces.web.common.ResultCode;
import com.csxuhuan.gelatoni.interfaces.web.common.UserHolder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * AI 对话接口
 *
 * <p>转发用户消息至 Python Bot 服务，获取回答后异步持久化问答记录（含意图和模型信息）。
 */
@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Value("${bot.chat-url}")
    private String botChatUrl;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ChatHistoryAppService chatHistoryAppService;

    public ChatController(ChatHistoryAppService chatHistoryAppService) {
        this.chatHistoryAppService = chatHistoryAppService;
    }

    /**
     * 发送消息并获取 AI 回答
     *
     * <p>请求体：{@code {"message": "...", "sessionUuid": "..."}}
     * <p>响应体：{@code {"answer": "..."}}
     * <p>Bot 响应中的 intent/intent_model/answer_model 会异步写入数据库，不影响响应时延。
     */
    @AuthCheck(permissionCode = PermissionConstants.PERM_AI_CHAT)
    @PostMapping
    public BaseResponse<Map<String, String>> chat(@RequestBody Map<String, String> body) {
        Long userId = UserHolder.getUserId();
        String sessionUuid = body.getOrDefault("sessionUuid", "default");
        String sessionId = userId + ":" + sessionUuid;
        String message = body.get("message");

        Map<String, String> payload = new HashMap<>();
        payload.put("message", message);
        payload.put("session_id", sessionId);
        try {
            Map<String, String> botResp = restTemplate.postForObject(botChatUrl, payload, Map.class);
            if (botResp == null) botResp = new HashMap<>();
            String answer = botResp.getOrDefault("answer", "");
            // 异步持久化，不阻塞响应
            chatHistoryAppService.saveChatAsync(
                    userId, sessionUuid, sessionId, message, answer,
                    botResp.get("intent"), botResp.get("intent_model"), botResp.get("answer_model")
            );
            Map<String, String> result = new HashMap<>();
            result.put("answer", answer);
            return BaseResponse.success(result);
        } catch (Exception e) {
            return BaseResponse.error(ResultCode.SYSTEM_ERROR, "Bot service error: " + e.getMessage());
        }
    }
}
