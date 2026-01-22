package com.csxuhuan.gelatoni.interfaces.web.request;

import javax.validation.constraints.NotBlank;

/**
 * 创建公告请求
 */
public class NoticeCreateRequest {
    /**
     * 公告标题
     */
    @NotBlank(message = "标题不能为空")
    private String title;

    /**
     * 公告内容
     */
    @NotBlank(message = "内容不能为空")
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
