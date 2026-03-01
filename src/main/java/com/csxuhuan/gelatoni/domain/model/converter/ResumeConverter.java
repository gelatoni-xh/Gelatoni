package com.csxuhuan.gelatoni.domain.model.converter;

import com.csxuhuan.gelatoni.domain.model.common.DeletedEnum;
import com.csxuhuan.gelatoni.domain.model.entity.Resume;
import com.csxuhuan.gelatoni.infrastructure.repository.entity.ResumeDO;

/**
 * 简历转换器
 *
 * @author csxuhuan
 */
public class ResumeConverter {

    /**
     * DO转实体
     */
    public static Resume toEntity(ResumeDO resumeDO) {
        if (resumeDO == null) {
            return null;
        }
        Resume resume = new Resume();
        resume.setId(resumeDO.getId());
        resume.setVersion(resumeDO.getVersion());
        resume.setName(resumeDO.getName());
        resume.setResumeData(resumeDO.getResumeData());
        resume.setDeleted(resumeDO.getDeleted() == 0 ? DeletedEnum.NOT_DELETED : DeletedEnum.DELETED);
        resume.setCreateTime(resumeDO.getCreateTime());
        resume.setUpdateTime(resumeDO.getUpdateTime());
        return resume;
    }

    /**
     * 实体转DO
     */
    public static ResumeDO toDO(Resume resume) {
        if (resume == null) {
            return null;
        }
        ResumeDO resumeDO = new ResumeDO();
        resumeDO.setId(resume.getId());
        resumeDO.setVersion(resume.getVersion());
        resumeDO.setName(resume.getName());
        resumeDO.setResumeData(resume.getResumeData());
        resumeDO.setDeleted(resume.getDeleted() == DeletedEnum.NOT_DELETED ? 0 : 1);
        resumeDO.setCreateTime(resume.getCreateTime());
        resumeDO.setUpdateTime(resume.getUpdateTime());
        return resumeDO;
    }
}