package com.csxuhuan.gelatoni.domain.query;

import com.csxuhuan.gelatoni.domain.model.entity.TodoItem;

/**
 * TODO项创建查询条件（领域对象）
 */
public class TodoItemCreateQuery {

    /**
     * TODO 内容
     */
    private String content;

    /**
     * 标签ID
     */
    private Long tagId;

    public TodoItemCreateQuery(String content, Long tagId) {
        this.content = content;
        this.tagId = tagId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    /**
     * 转换为领域层实体
     *
     * @return 领域层实体
     */
    public TodoItem toTodoItem() {
        return new TodoItem(null, content, false, tagId, null, null);
    }
}
