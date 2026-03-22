package com.csxuhuan.gelatoni.infrastructure.client.llm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpenAiChatRequest {
    private String model;
    private List<ChatMessage> messages;
}
