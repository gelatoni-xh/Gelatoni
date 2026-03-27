package com.csxuhuan.gelatoni.application.service.impl;

import com.csxuhuan.gelatoni.application.service.WikipediaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Wikipedia 服务实现
 */
@Service
public class WikipediaServiceImpl implements WikipediaService {

    private static final Logger logger = LoggerFactory.getLogger(WikipediaServiceImpl.class);

    private final RestTemplate restTemplate;

    public WikipediaServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Map<String, String> fetchRunnerData(String name) {
        try {
            String url = "https://ja.wikipedia.org/w/api.php?action=query&format=json&titles=" +
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

            Map<String, String> result = new HashMap<>();
            result.put("title", title);
            result.put("content", content);

            return result;

        } catch (Exception e) {
            logger.error("[Wiki] Error for {}: {}", name, e.getMessage());
            return null;
        }
    }
}


