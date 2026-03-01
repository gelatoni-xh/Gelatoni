package com.csxuhuan.gelatoni.application.dto;

import java.time.LocalDateTime;

/**
 * 简历DTO
 *
 * @author csxuhuan
 */
public class ResumeDTO {

    /** 主键ID */
    private Long id;

    /** 版本号 */
    private Integer version;

    /** 简历名称 */
    private String name;

    /** 简历数据（JSON格式） */
    private String resumeData;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;

    public ResumeDTO() {
    }

    public ResumeDTO(Long id, Integer version, String name, String resumeData, 
                     LocalDateTime createTime, LocalDateTime updateTime) {
        this.id = id;
        this.version = version;
        this.name = name;
        this.resumeData = resumeData;
        this.createTime = createTime;
        this.updateTime = updateTime;
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
        return "ResumeDTO{" +
                "id=" + id +
                ", version=" + version +
                ", name='" + name + '\'' +
                ", resumeData='" + resumeData + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}