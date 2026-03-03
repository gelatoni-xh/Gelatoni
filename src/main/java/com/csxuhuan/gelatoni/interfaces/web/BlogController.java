package com.csxuhuan.gelatoni.interfaces.web;

import com.csxuhuan.gelatoni.application.dto.BlogArticleDTO;
import com.csxuhuan.gelatoni.application.dto.BlogContentDTO;
import com.csxuhuan.gelatoni.infrastructure.knowledgebase.KnowledgeBaseService;
import com.csxuhuan.gelatoni.interfaces.config.AuthCheck;
import com.csxuhuan.gelatoni.interfaces.web.common.BaseResponse;
import com.csxuhuan.gelatoni.interfaces.web.common.PermissionConstants;
import com.csxuhuan.gelatoni.interfaces.web.common.ResultCode;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/blog")
public class BlogController {

    private final KnowledgeBaseService knowledgeBaseService;

    public BlogController(KnowledgeBaseService knowledgeBaseService) {
        this.knowledgeBaseService = knowledgeBaseService;
    }

    @AuthCheck(permissionCode = PermissionConstants.PERM_BLOG_VIEW)
    @GetMapping(value = "/categories", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<List<String>> getCategories() {
        List<String> categories = knowledgeBaseService.getCategoryTree();
        return BaseResponse.success(categories);
    }

    @AuthCheck(permissionCode = PermissionConstants.PERM_BLOG_VIEW)
    @GetMapping(value = "/articles", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<List<BlogArticleDTO>> getArticles(@RequestParam String category) {
        List<Map<String, String>> articles = knowledgeBaseService.getArticlesByCategory(category);
        List<BlogArticleDTO> dtoList = articles.stream().map(m -> {
            BlogArticleDTO dto = new BlogArticleDTO();
            dto.setTitle(m.get("title"));
            dto.setCategory(m.get("category"));
            return dto;
        }).collect(Collectors.toList());
        return BaseResponse.success(dtoList);
    }

    @AuthCheck(permissionCode = PermissionConstants.PERM_BLOG_VIEW)
    @GetMapping(value = "/content", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<BlogContentDTO> getContent(@RequestParam String category, @RequestParam String title) {
        String content = knowledgeBaseService.getArticleContent(category, title);
        if (content == null) {
            return BaseResponse.error(ResultCode.BIZ_ERROR, "文章不存在");
        }
        BlogContentDTO dto = new BlogContentDTO();
        dto.setTitle(title);
        dto.setCategory(category);
        dto.setContent(content);
        return BaseResponse.success(dto);
    }
}
