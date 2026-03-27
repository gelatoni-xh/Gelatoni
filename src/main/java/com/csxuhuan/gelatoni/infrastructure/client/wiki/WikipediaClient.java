package com.csxuhuan.gelatoni.infrastructure.client.wiki;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class WikipediaClient {

    private static final Logger logger = LoggerFactory.getLogger(WikipediaClient.class);

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
        try {
            String url = apiUrl + "?action=query&format=json&titles=" +
                    java.net.URLEncoder.encode(name, "UTF-8") +
                    "&prop=extracts&explaintext=true";

            Map<String, Object> response = restTemplate.getForObject(url, Map.class);

            if (response == null) {
                logger.error("[Wiki] Response is null for: {}", name);
                return null;
            }

            Map<String, Object> query = (Map<String, Object>) response.get("query");
            Map<String, Object> pages = (Map<String, Object>) query.get("pages");
            String pageId = (String) pages.keySet().iterator().next();

            if ("-1".equals(pageId)) {
                logger.warn("[Wiki] Not found: {}", name);
                return null;
            }

            Map<String, Object> page = (Map<String, Object>) pages.get(pageId);
            String title = (String) page.get("title");
            String content = (String) page.get("extract");

            logger.info("[Wiki] Found: {} -> {}", name, title);

            WikiPageResult result = new WikiPageResult();
            result.setTitle(title);
            result.setContent(content);
            return result;

        } catch (Exception e) {
            logger.error("[Wiki] Error for {}: {}", name, e.getMessage());
            return null;
        }
    }
}
