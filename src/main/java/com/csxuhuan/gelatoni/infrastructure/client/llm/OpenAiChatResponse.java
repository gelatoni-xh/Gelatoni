package com.csxuhuan.gelatoni.infrastructure.client.llm;

import lombok.Data;

import java.util.List;

@Data
public class OpenAiChatResponse {
    private List<Choice> choices;

    @Data
    public static class Choice {
        private ChatMessage message;
    }
}
