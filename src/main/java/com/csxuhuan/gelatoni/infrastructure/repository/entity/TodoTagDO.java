package com.csxuhuan.gelatoni.infrastructure.repository.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

/**
 * TodoTagDO
 *
 * 数据库表 todo_tag 的直接映射对象
 * 只用于持久化层，不包含任何业务语义
 */
@TableName("todo_tag")
public class TodoTagDO {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 标签名称
     */
    private String name;

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

    public TodoTagDO() {
    }

    public TodoTagDO(Long id, String name, LocalDateTime createTime, LocalDateTime modifiedTime, Boolean isDeleted) {
        this.id = id;
        this.name = name;
        this.createTime = createTime;
        this.modifiedTime = modifiedTime;
        this.isDeleted = isDeleted;
    }

    public TodoTagDO(Long id, String name, Long userId, Long creator, Long modifier,
                     LocalDateTime createTime, LocalDateTime modifiedTime, Boolean isDeleted) {
        this.id = id;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
