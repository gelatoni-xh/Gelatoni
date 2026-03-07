package com.csxuhuan.gelatoni.interfaces.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.csxuhuan.gelatoni.application.dto.ChatMessageDTO;
import com.csxuhuan.gelatoni.application.dto.ChatSessionDTO;
import com.csxuhuan.gelatoni.application.service.ChatHistoryAppService;
import com.csxuhuan.gelatoni.domain.model.entity.ChatMessage;
import com.csxuhuan.gelatoni.domain.model.entity.ChatSession;
import com.csxuhuan.gelatoni.infrastructure.repository.ChatMessageRepository;
import com.csxuhuan.gelatoni.infrastructure.repository.ChatSessionRepository;
import com.csxuhuan.gelatoni.interfaces.config.AuthCheck;
import com.csxuhuan.gelatoni.interfaces.web.common.BaseResponse;
import com.csxuhuan.gelatoni.interfaces.web.common.PageData;
import com.csxuhuan.gelatoni.interfaces.web.common.PermissionConstants;
import com.csxuhuan.gelatoni.interfaces.web.common.ResultCode;
import com.csxuhuan.gelatoni.interfaces.web.common.UserHolder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private final ChatSessionRepository chatSessionRepository;
    private final ChatMessageRepository chatMessageRepository;

    public ChatController(ChatHistoryAppService chatHistoryAppService,
                         ChatSessionRepository chatSessionRepository,
                         ChatMessageRepository chatMessageRepository) {
        this.chatHistoryAppService = chatHistoryAppService;
        this.chatSessionRepository = chatSessionRepository;
        this.chatMessageRepository = chatMessageRepository;
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

    /**
     * 分页查询用户会话列表
     *
     * <p>查询参数：pageNo（页码，从1开始）、pageSize（每页大小）
     * <p>响应体：分页结果，会话按修改时间倒序
     */
    @AuthCheck(permissionCode = PermissionConstants.PERM_AI_CHAT)
    @GetMapping("/sessions")
    public BaseResponse<PageData<ChatSessionDTO>> getSessions(
            @RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        Long userId = UserHolder.getUserId();
        IPage<ChatSession> page = chatSessionRepository.pageByUserId(userId, pageNo, pageSize);
        List<ChatSessionDTO> dtoList = page.getRecords().stream().map(session -> {
            ChatSessionDTO dto = new ChatSessionDTO();
            dto.setSessionUuid(session.getSessionUuid());
            dto.setTitle(session.getTitle());
            dto.setCreateTime(session.getCreateTime());
            dto.setModifiedTime(session.getModifiedTime());
            dto.setRoundCount(session.getRoundCount());
            return dto;
        }).collect(Collectors.toList());
        PageData<ChatSessionDTO> result = new PageData<>(dtoList, page.getTotal(), (int) page.getCurrent(), (int) page.getSize());
        return BaseResponse.success(result);
    }

    /**
     * 查询会话的所有消息
     *
     * <p>查询参数：sessionUuid（会话UUID）
     * <p>响应体：消息列表，按创建时间倒序
     */
    @AuthCheck(permissionCode = PermissionConstants.PERM_AI_CHAT)
    @GetMapping("/messages")
    public BaseResponse<List<ChatMessageDTO>> getMessages(@RequestParam String sessionUuid) {
        Long userId = UserHolder.getUserId();
        String sessionId = userId + ":" + sessionUuid;
        List<ChatMessage> messages = chatMessageRepository.findBySessionIdDesc(sessionId);
        List<ChatMessageDTO> dtoList = messages.stream().map(msg -> {
            ChatMessageDTO dto = new ChatMessageDTO();
            dto.setMessage(msg.getMessage());
            dto.setAnswer(msg.getAnswer());
            dto.setCreateTime(msg.getCreateTime());
            return dto;
        }).collect(Collectors.toList());
        return BaseResponse.success(dtoList);
    }

    /**
     * 内部持久化接口（供 Python Bot 调用，仅限本机访问）
     */
    @PostMapping("/internal/persist")
    public BaseResponse<Map<String, Boolean>> persistChat(
            HttpServletRequest request,
            @RequestBody Map<String, Object> body) {
        if (!"127.0.0.1".equals(request.getRemoteAddr()) && !"0:0:0:0:0:0:0:1".equals(request.getRemoteAddr())) {
            return BaseResponse.error(ResultCode.UNAUTHORIZED, "Access denied");
        }
        try {
            Long userId = Long.valueOf(body.get("userId").toString());
            String sessionUuid = (String) body.get("sessionUuid");
            String sessionId = (String) body.get("sessionId");
            String message = (String) body.get("message");
            String answer = (String) body.get("answer");
            String intent = (String) body.get("intent");
            String intentModel = (String) body.get("intentModel");
            String answerModel = (String) body.get("answerModel");

            // 异步持久化
            chatHistoryAppService.saveChatAsync(
                    userId, sessionUuid, sessionId, message, answer,
                    intent, intentModel, answerModel
            );

            Map<String, Boolean> result = new HashMap<>();
            result.put("success", true);
            return BaseResponse.success(result);
        } catch (Exception e) {
            return BaseResponse.error(ResultCode.SYSTEM_ERROR, "Persist failed: " + e.getMessage());
        }
    }
}
