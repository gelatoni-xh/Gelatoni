package com.csxuhuan.gelatoni.domain.query;

import com.csxuhuan.gelatoni.domain.model.entity.Notice;

/**
 * 公告创建查询条件（领域对象）
 */
public class NoticeCreateQuery {
    /**
     * 公告标题
     */
    private String title;

    /**
     * 公告内容
     */
    private String content;

    public NoticeCreateQuery(String title, String content) {
        this.title = title;
        this.content = content;
    }

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

    /**
     * 转换为领域层实体
     * @return 领域层实体
     */
    public Notice toNotice() {
        return new Notice(null, title, content, null, null);
    }
}
