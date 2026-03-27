package com.csxuhuan.gelatoni.application.service.impl;

import com.csxuhuan.gelatoni.application.service.WikipediaService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Wikipedia 服务实现
 */
@Service
public class WikipediaServiceImpl implements WikipediaService {

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
                return null;
            }

            Map<String, Object> query = (Map<String, Object>) response.get("query");
            if (query == null) {
                return null;
            }

            Map<String, Object> pages = (Map<String, Object>) query.get("pages");
            if (pages == null || pages.isEmpty()) {
                return null;
            }

            Map<String, Object> page = (Map<String, Object>) pages.values().iterator().next();
            String pageId = (String) pages.keySet().iterator().next();

            if ("-1".equals(pageId) || !page.containsKey("extract")) {
                return null;
            }

            Map<String, String> result = new HashMap<>();
            result.put("title", (String) page.get("title"));
            result.put("content", (String) page.get("extract"));

            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
