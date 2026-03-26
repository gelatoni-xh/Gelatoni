package com.csxuhuan.gelatoni.infrastructure.repository.ekiden.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

/**
 * RunnerRelationshipDO
 *
 * 数据库表 runner_relationship 的直接映射对象
 * 只用于持久化层，不包含任何业务语义
 */
@TableName("runner_relationship")
public class RunnerRelationshipDO {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 选手A
     */
    private Long runnerIdA;

    /**
     * 选手B
     */
    private Long runnerIdB;

    /**
     * 关系类型：SAME_HIGH_SCHOOL/SAME_UNIVERSITY/SAME_CORPORATE/RIVAL/CP
     */
    private String relationType;

    /**
     * 关系描述
     */
    private String description;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    public RunnerRelationshipDO() {
    }

    public RunnerRelationshipDO(Long id, Long runnerIdA, Long runnerIdB, String relationType, String description, LocalDateTime createdAt) {
        this.id = id;
        this.runnerIdA = runnerIdA;
        this.runnerIdB = runnerIdB;
        this.relationType = relationType;
        this.description = description;
        this.createdAt = createdAt;
    }

    // ===== Getter / Setter =====

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRunnerIdA() {
        return runnerIdA;
    }

    public void setRunnerIdA(Long runnerIdA) {
        this.runnerIdA = runnerIdA;
    }

    public Long getRunnerIdB() {
        return runnerIdB;
    }

    public void setRunnerIdB(Long runnerIdB) {
        this.runnerIdB = runnerIdB;
    }

    public String getRelationType() {
        return relationType;
    }

    public void setRelationType(String relationType) {
        this.relationType = relationType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
