package com.csxuhuan.gelatoni.domain.model.entity;

import java.time.LocalDateTime;

/**
 * TODO 项领域实体
 *
 * <p>代表一个"有效的 TODO 待办事项"，是领域模型的核心对象。
 * 领域实体只关注业务属性，不关心数据库实现细节。
 *
 * <p>业务属性：
 * <ul>
 *     <li>content - TODO 内容描述</li>
 *     <li>completed - 完成状态</li>
 *     <li>tagId - 关联的标签（可选）</li>
 * </ul>
 *
 * <p>设计说明：
 * 采用不可变对象模式，所有字段为 final，保证线程安全和数据一致性。
 *
 * @author csxuhuan
 */
public class TodoItem {

    /** TODO 项唯一标识 */
    private final Long id;

    /** TODO 内容 */
    private final String content;

    /** 是否已完成 */
    private final Boolean completed;

    /** 关联的标签 ID，可为 null 表示无标签 */
    private final Long tagId;

    /** 创建时间 */
    private final LocalDateTime createTime;

    /** 最后修改时间 */
    private final LocalDateTime modifiedTime;

    /**
     * 构造 TODO 项领域对象
     *
     * @param id           TODO 项 ID，新建时可为 null
     * @param content      TODO 内容
     * @param completed    是否完成，新建时默认为 false
     * @param tagId        关联的标签 ID，可为 null
     * @param createTime   创建时间，新建时可为 null（由数据库填充）
     * @param modifiedTime 修改时间，新建时可为 null（由数据库填充）
     */
    public TodoItem(Long id, String content, Boolean completed, Long tagId,
                    LocalDateTime createTime, LocalDateTime modifiedTime) {
        this.id = id;
        this.content = content;
        this.completed = completed;
        this.tagId = tagId;
        this.createTime = createTime;
        this.modifiedTime = modifiedTime;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public Long getTagId() {
        return tagId;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public LocalDateTime getModifiedTime() {
        return modifiedTime;
    }
}
