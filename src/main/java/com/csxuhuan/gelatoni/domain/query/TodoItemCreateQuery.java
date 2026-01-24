package com.csxuhuan.gelatoni.domain.query;

import com.csxuhuan.gelatoni.domain.model.entity.TodoItem;

/**
 * TODO 项创建查询对象
 *
 * <p>封装创建 TODO 项所需的参数。
 *
 * @author csxuhuan
 */
public class TodoItemCreateQuery {

    /** TODO 内容 */
    private String content;

    /** 关联的标签 ID，可为 null */
    private Long tagId;

    /**
     * 构造 TODO 项创建查询对象
     *
     * @param content TODO 内容
     * @param tagId   标签 ID，可为 null
     */
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
     * 转换为 TODO 项领域实体
     *
     * <p>工厂方法，将查询对象转换为领域实体。
     * 新建的 TODO 项默认为未完成状态（completed = false）。
     *
     * @return 新建的 TODO 项领域实体（未持久化）
     */
    public TodoItem toTodoItem() {
        return new TodoItem(null, content, false, tagId, null, null);
    }
}
