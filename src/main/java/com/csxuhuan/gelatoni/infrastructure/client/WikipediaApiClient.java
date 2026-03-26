package com.csxuhuan.gelatoni.infrastructure.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * WikipediaApiClient
 *
 * 调用日文 Wikipedia API 获取选手信息
 */
@Slf4j
@Component
public class WikipediaApiClient {

    @Value("${wiki.api-url:https://ja.wikipedia.org/w/api.php}")
    private String apiUrl;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public WikipediaApiClient(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    /**
     * 获取选手页面内容
     *
     * @param name 选手名字
     * @return 页面内容，如果未找到返回 null
     */
    public String fetchRunnerContent(String name) {
        try {
            Map<String, String> params = new HashMap<>();
            params.put("action", "query");
            params.put("format", "json");
            params.put("titles", name);
            params.put("prop", "extracts");
            params.put("explaintext", "true");

            String url = buildUrl(params);
            log.info("[Wiki] Fetching: {}", name);

            String response = restTemplate.getForObject(url, String.class);
            JsonNode root = objectMapper.readTree(response);

            JsonNode pages = root.path("query").path("pages");
            if (pages.isEmpty()) {
                log.warn("[Wiki] No pages found for: {}", name);
                return null;
            }

            JsonNode page = pages.elements().next();
            String title = page.path("title").asText();
            String extract = page.path("extract").asText();

            if (extract.isEmpty()) {
                log.warn("[Wiki] Empty extract for: {}", name);
                return null;
            }

            log.info("[Wiki] Success: {} -> ", name, title);
            return extract;

        } catch (Exception e) {
            log.error("[Wiki] Error fetching {}: {}", name, e.getMessage());
            return null;
        }
    }

    /**
     * 获取选手页面标题
     *
     * @param name 选手名字
     * @return 实际的 Wikipedia 页面标题
     */
    public String fetchRunnerTitle(String name) {
        try {
            Map<String, String> params = new HashMap<>();
            params.put("action", "query");
            params.put("format", "json");
            params.put("titles", name);

            String url = buildUrl(params);
            String response = restTemplate.getForObject(url, String.class);
            JsonNode root = objectMapper.readTree(response);

            JsonNode pages = root.path("query").path("pages");
            if (pages.isEmpty()) {
                return null;
            }

            JsonNode page = pages.elements().next();
            return page.path("title").asText();

        } catch (Exception e) {
            log.error("[Wiki] Error fetching title for {}: {}", name, e.getMessage());
            return null;
        }
    }

    private String buildUrl(Map<String, String> params) {
        StringBuilder sb = new StringBuilder(apiUrl);
        sb.append("?");
        params.forEach((k, v) -> sb.append(k).append("=").append(encodeParam(v)).append("&"));
        return sb.toString();
    }

    private String encodeParam(String param) {
        try {
            return java.net.URLEncoder.encode(param, "UTF-8");
        } catch (Exception e) {
            return param;
        }
    }
}
