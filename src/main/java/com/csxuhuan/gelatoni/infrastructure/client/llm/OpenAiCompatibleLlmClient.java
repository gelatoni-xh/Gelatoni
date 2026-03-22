package com.csxuhuan.gelatoni.infrastructure.client.llm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * OpenAI Compatible API 实现
 * <p>支持 OpenRouter、各厂商 OpenAI 兼容接口</p>
 */
@Service
public class OpenAiCompatibleLlmClient implements LlmClient {

    private final RestTemplate restTemplate;

    @Value("${llm.base-url}")
    private String baseUrl;

    @Value("${llm.api-key}")
    private String apiKey;

    @Value("${llm.model}")
    private String model;

    public OpenAiCompatibleLlmClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String chat(List<ChatMessage> messages) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        OpenAiChatRequest request = new OpenAiChatRequest(model, messages);
        HttpEntity<OpenAiChatRequest> entity = new HttpEntity<>(request, headers);

        OpenAiChatResponse response = restTemplate.postForObject(
                baseUrl + "/chat/completions", entity, OpenAiChatResponse.class);

        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
            throw new RuntimeException("LLM 返回结果为空");
        }
        return response.getChoices().get(0).getMessage().getContent();
    }
}
