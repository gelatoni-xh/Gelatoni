package com.csxuhuan.gelatoni.infrastructure.knowledgebase;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class KnowledgeBaseService {

    @Value("${knowledgebase.path:/srv/knowledge_base}")
    private String basePath;

    public List<String> getCategoryTree() {
        try (Stream<Path> paths = Files.walk(Paths.get(basePath), 1)) {
            return paths
                    .filter(Files::isDirectory)
                    .filter(p -> !p.equals(Paths.get(basePath)))
                    .filter(p -> !p.getFileName().toString().startsWith("."))
                    .map(p -> p.getFileName().toString())
                    .sorted()
                    .collect(Collectors.toList());
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }

    public List<Map<String, String>> getArticlesByCategory(String category) {
        Path categoryPath = Paths.get(basePath, category);
        if (!Files.exists(categoryPath) || !Files.isDirectory(categoryPath)) {
            return Collections.emptyList();
        }

        try (Stream<Path> paths = Files.walk(categoryPath, 1)) {
            return paths
                    .filter(Files::isRegularFile)
                    .filter(p -> p.toString().endsWith(".md"))
                    .map(p -> {
                        Map<String, String> article = new HashMap<>();
                        article.put("title", p.getFileName().toString().replace(".md", ""));
                        article.put("category", category);
                        return article;
                    })
                    .sorted(Comparator.comparing(m -> m.get("title")))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }

    public String getArticleContent(String category, String title) {
        Path filePath = Paths.get(basePath, category, title + ".md");
        if (!Files.exists(filePath) || !Files.isRegularFile(filePath)) {
            return null;
        }

        try {
            return new String(Files.readAllBytes(filePath), "UTF-8");
        } catch (IOException e) {
            return null;
        }
    }
}
