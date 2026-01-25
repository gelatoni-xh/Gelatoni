package com.csxuhuan.gelatoni.infrastructure.repository.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

/**
 * TodoItemDO
 *
 * 数据库表 todo_item 的直接映射对象
 * 只用于持久化层，不包含任何业务语义
 */
@TableName("todo_item")
public class TodoItemDO {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * TODO 内容
     */
    private String content;

    /**
     * 是否完成：0-未完成，1-已完成
     */
    private Boolean completed;

    /**
     * 标签ID
     */
    private Long tagId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime modifiedTime;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 创建人
     */
    private Long creator;

    /**
     * 修改人
     */
    private Long modifier;

    /**
     * 逻辑删除标记
     * 0 - 未删除
     * 1 - 已删除
     */
    private Boolean isDeleted;

    public TodoItemDO() {
    }

    public TodoItemDO(Long id, String content, Boolean completed, Long tagId,
                      LocalDateTime createTime, LocalDateTime modifiedTime, Boolean isDeleted) {
        this.id = id;
        this.content = content;
        this.completed = completed;
        this.tagId = tagId;
        this.createTime = createTime;
        this.modifiedTime = modifiedTime;
        this.isDeleted = isDeleted;
    }

    public TodoItemDO(Long id, String content, Boolean completed, Long tagId, Long userId,
                      Long creator, Long modifier, LocalDateTime createTime, LocalDateTime modifiedTime, Boolean isDeleted) {
        this.id = id;
        this.content = content;
        this.completed = completed;
        this.tagId = tagId;
        this.userId = userId;
        this.creator = creator;
        this.modifier = modifier;
        this.createTime = createTime;
        this.modifiedTime = modifiedTime;
        this.isDeleted = isDeleted;
    }

    // ===== Getter / Setter =====

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

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(LocalDateTime modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public Long getModifier() {
        return modifier;
    }

    public void setModifier(Long modifier) {
        this.modifier = modifier;
    }
}
