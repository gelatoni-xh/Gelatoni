package com.csxuhuan.gelatoni.domain.query;

import com.csxuhuan.gelatoni.domain.model.entity.TodoItem;

/**
 * TODO 项更新查询对象
 *
 * <p>封装更新 TODO 项所需的参数。支持部分更新，
 * 只更新非 null 的字段。
 *
 * @author csxuhuan
 */
public class TodoItemUpdateQuery {

    /** TODO 项 ID，必填 */
    private Long id;

    /** TODO 内容，null 表示不更新 */
    private String content;

    /** 是否完成，null 表示不更新 */
    private Boolean completed;

    /** 标签 ID，null 表示不更新 */
    private Long tagId;

    /**
     * 构造 TODO 项更新查询对象
     *
     * @param id        TODO 项 ID，必填
     * @param content   TODO 内容
     * @param completed 是否完成
     * @param tagId     标签 ID
     */
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
     * 转换为 TODO 项领域实体
     *
     * <p>工厂方法，将查询对象转换为领域实体，用于更新操作。
     *
     * @return TODO 项领域实体
     */
    public TodoItem toTodoItem() {
        return new TodoItem(id, content, completed, tagId, null, null);
    }
}
