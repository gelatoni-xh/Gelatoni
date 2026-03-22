package com.csxuhuan.gelatoni.infrastructure.client.wiki;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WikipediaClient {

    private final RestTemplate restTemplate;

    @Value("${wiki.api-url:https://ja.wikipedia.org/w/api.php}")
    private String apiUrl;

    public WikipediaClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * 根据选手名字获取日文 Wikipedia 页面内容
     */
    public WikiPageResult fetchPage(String name) {
        String url = apiUrl + "?action=query&titles={title}&prop=extracts&explaintext=true&format=json";

        JsonNode root = restTemplate.getForObject(url, JsonNode.class, name);
        JsonNode pages = root.path("query").path("pages");
        JsonNode page = pages.elements().next();

        if (page.has("missing")) {
            return null;
        }

        WikiPageResult result = new WikiPageResult();
        result.setTitle(page.path("title").asText());
        result.setContent(page.path("extract").asText());
        return result;
    }
}
