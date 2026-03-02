package com.csxuhuan.gelatoni.domain.model.entity;

import com.csxuhuan.gelatoni.domain.model.common.DeletedEnum;

import java.time.LocalDateTime;

/**
 * 简历实体
 *
 * @author csxuhuan
 */
public class Resume {

    /** 主键ID */
    private Long id;

    /** 版本号 */
    private Integer version;

    /** 简历名称 */
    private String name;

    /** 简历数据（JSON格式） */
    private String resumeData;

    /** 版本变更点（支持Markdown格式） */
    private String changelog;

    /** 删除标识 */
    private DeletedEnum deleted;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;

    public Resume() {
    }

    public Resume(Integer version, String name, String resumeData) {
        this.version = version;
        this.name = name;
        this.resumeData = resumeData;
        this.changelog = null;
        this.deleted = DeletedEnum.NOT_DELETED;
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
    }

    public Resume(Integer version, String name, String resumeData, String changelog) {
        this.version = version;
        this.name = name;
        this.resumeData = resumeData;
        this.changelog = changelog;
        this.deleted = DeletedEnum.NOT_DELETED;
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
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

    public DeletedEnum getDeleted() {
        return deleted;
    }

    public void setDeleted(DeletedEnum deleted) {
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
        return "Resume{" +
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