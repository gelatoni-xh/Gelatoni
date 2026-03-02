package com.csxuhuan.gelatoni.infrastructure.repository.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

/**
 * 简历数据对象
 *
 * 数据库表 resume 的直接映射对象
 * 只用于持久化层，不包含任何业务语义
 *
 * @author csxuhuan
 */
@TableName("resume")
public class ResumeDO {

    /** 主键ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 版本号 */
    private Integer version;

    /** 简历名称 */
    private String name;

    /** 简历数据（JSON格式） */
    private String resumeData;

    /** 版本变更点（支持Markdown格式） */
    private String changelog;

    /** 删除标识：0-未删除，1-已删除 */
    private Integer deleted;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;

    public ResumeDO() {
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResumeData() {
        return resumeData;
    }

    public void setResumeData(String resumeData) {
        this.resumeData = resumeData;
    }

    public String getChangelog() {
        return changelog;
    }

    public void setChangelog(String changelog) {
        this.changelog = changelog;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "ResumeDO{" +
                "id=" + id +
                ", version=" + version +
                ", name='" + name + '\'' +
                ", resumeData='" + resumeData + '\'' +
                ", changelog='" + changelog + '\'' +
                ", deleted=" + deleted +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}