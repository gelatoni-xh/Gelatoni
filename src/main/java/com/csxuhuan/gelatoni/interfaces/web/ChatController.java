package com.csxuhuan.gelatoni.interfaces.web;

import com.csxuhuan.gelatoni.interfaces.config.AuthCheck;
import com.csxuhuan.gelatoni.interfaces.web.common.BaseResponse;
import com.csxuhuan.gelatoni.interfaces.web.common.PermissionConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Value("${bot.chat-url}")
    private String botChatUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    @AuthCheck(permissionCode = PermissionConstants.PERM_AI_CHAT)
    @PostMapping
    public BaseResponse<Map<String, String>> chat(
            @RequestBody Map<String, String> body,
            @RequestAttribute("userId") String userId) {
        String sessionUuid = body.getOrDefault("sessionUuid", "default");
        String sessionId = userId + ":" + sessionUuid;
        
        Map<String, String> payload = new HashMap<>();
        payload.put("message", body.get("message"));
        payload.put("session_id", sessionId);
        Map<String, String> botResp = restTemplate.postForObject(botChatUrl, payload, Map.class);
        return BaseResponse.success(botResp);
    }
}
