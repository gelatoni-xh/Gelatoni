package com.csxuhuan.gelatoni.interfaces.web;

import com.csxuhuan.gelatoni.interfaces.config.AuthCheck;
import com.csxuhuan.gelatoni.interfaces.web.common.BaseResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Value("${bot.chat-url}")
    private String botChatUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    @AuthCheck
    @PostMapping
    public BaseResponse<Map<String, String>> chat(@RequestBody Map<String, String> body) {
        Map<String, String> botResp = restTemplate.postForObject(botChatUrl, body, Map.class);
        return BaseResponse.success(botResp);
    }
}
