package com.csxuhuan.gelatoni.infrastructure.repository.ekiden.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

/**
 * RunnerTagDO
 *
 * 数据库表 runner_tag 的直接映射对象
 * 只用于持久化层，不包含任何业务语义
 */
@TableName("runner_tag")
public class RunnerTagDO {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 选手ID
     */
    private Long runnerId;

    /**
     * 标签类型：SCHOOL/ERA/FEATURE
     */
    private String tagType;

    /**
     * 标签值
     */
    private String tagValue;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    public RunnerTagDO() {
    }

    public RunnerTagDO(Long id, Long runnerId, String tagType, String tagValue, LocalDateTime createdAt) {
        this.id = id;
        this.runnerId = runnerId;
        this.tagType = tagType;
        this.tagValue = tagValue;
        this.createdAt = createdAt;
    }

    // ===== Getter / Setter =====

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRunnerId() {
        return runnerId;
    }

    public void setRunnerId(Long runnerId) {
        this.runnerId = runnerId;
    }

    public String getTagType() {
        return tagType;
    }

    public void setTagType(String tagType) {
        this.tagType = tagType;
    }

    public String getTagValue() {
        return tagValue;
    }

    public void setTagValue(String tagValue) {
        this.tagValue = tagValue;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
