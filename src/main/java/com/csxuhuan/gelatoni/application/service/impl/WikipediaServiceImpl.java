package com.csxuhuan.gelatoni.application.service.impl;

import com.csxuhuan.gelatoni.application.service.WikipediaService;
import com.csxuhuan.gelatoni.infrastructure.client.wiki.WikipediaClient;
import com.csxuhuan.gelatoni.infrastructure.client.wiki.WikiPageResult;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Wikipedia 服务实现
 */
@Service
public class WikipediaServiceImpl implements WikipediaService {

    private final WikipediaClient wikipediaClient;

    public WikipediaServiceImpl(WikipediaClient wikipediaClient) {
        this.wikipediaClient = wikipediaClient;
    }

    @Override
    public Map<String, String> fetchRunnerData(String name) {
        WikiPageResult result = wikipediaClient.fetchPage(name);

        if (result == null) {
            return null;
        }

        Map<String, String> data = new HashMap<>();
        data.put("title", result.getTitle());
        data.put("content", result.getContent());
        return data;
    }
}
