package com.csxuhuan.gelatoni.application.service.impl;

import com.csxuhuan.gelatoni.application.assembler.ResumeAssembler;
import com.csxuhuan.gelatoni.application.dto.ResumeDTO;
import com.csxuhuan.gelatoni.application.service.ResumeAppService;
import com.csxuhuan.gelatoni.domain.model.entity.Resume;
import com.csxuhuan.gelatoni.domain.service.ResumeDomainService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 简历应用服务实现
 *
 * @author csxuhuan
 */
@Service
public class ResumeAppServiceImpl implements ResumeAppService {

    private final ResumeDomainService resumeDomainService;
    private final ResumeAssembler resumeAssembler = new ResumeAssembler();

    public ResumeAppServiceImpl(ResumeDomainService resumeDomainService) {
        this.resumeDomainService = resumeDomainService;
    }

    @Override
    public List<ResumeDTO> findAllResumes() {
        List<Resume> resumes = resumeDomainService.findAllResumes();
        return resumeAssembler.toDTOList(resumes);
    }
}