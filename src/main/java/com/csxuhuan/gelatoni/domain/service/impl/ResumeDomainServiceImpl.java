package com.csxuhuan.gelatoni.domain.service.impl;

import com.csxuhuan.gelatoni.domain.model.entity.Resume;
import com.csxuhuan.gelatoni.domain.service.ResumeDomainService;
import com.csxuhuan.gelatoni.infrastructure.repository.ResumeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 简历领域服务实现
 *
 * @author csxuhuan
 */
@Service
public class ResumeDomainServiceImpl implements ResumeDomainService {

    private final ResumeRepository resumeRepository;

    public ResumeDomainServiceImpl(ResumeRepository resumeRepository) {
        this.resumeRepository = resumeRepository;
    }

    @Override
    public List<Resume> findAllResumes() {
        return resumeRepository.findAllOrderByVersionDesc();
    }
}