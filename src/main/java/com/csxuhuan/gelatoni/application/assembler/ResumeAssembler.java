package com.csxuhuan.gelatoni.application.assembler;

import com.csxuhuan.gelatoni.application.dto.ResumeDTO;
import com.csxuhuan.gelatoni.domain.model.entity.Resume;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 简历装配器
 *
 * @author csxuhuan
 */
public class ResumeAssembler {

    /**
     * 实体转DTO
     */
    public ResumeDTO toDTO(Resume resume) {
        if (resume == null) {
            return null;
        }
        return new ResumeDTO(
                resume.getId(),
                resume.getVersion(),
                resume.getName(),
                resume.getResumeData(),
                resume.getChangelog(),
                resume.getCreateTime(),
                resume.getUpdateTime()
        );
    }

    /**
     * 实体列表转DTO列表
     */
    public List<ResumeDTO> toDTOList(List<Resume> resumes) {
        if (resumes == null) {
            return null;
        }
        return resumes.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}