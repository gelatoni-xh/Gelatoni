package com.csxuhuan.gelatoni.domain.query;

import com.csxuhuan.gelatoni.domain.model.entity.TodoItem;

/**
 * TODO项更新查询条件（领域对象）
 */
public class TodoItemUpdateQuery {

    /**
     * TODO项ID
     */
    private Long id;

    /**
     * TODO 内容
     */
    private String content;

    /**
     * 是否完成
     */
    private Boolean completed;

    /**
     * 标签ID
     */
    private Long tagId;

    public TodoItemUpdateQuery(Long id, String content, Boolean completed, Long tagId) {
        this.id = id;
        this.content = content;
        this.completed = completed;
        this.tagId = tagId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
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
        return new TodoItem(id, content, completed, tagId, null, null);
    }
}
