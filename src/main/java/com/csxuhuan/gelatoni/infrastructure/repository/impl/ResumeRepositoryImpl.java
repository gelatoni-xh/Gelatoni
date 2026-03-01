package com.csxuhuan.gelatoni.infrastructure.repository.impl;

import com.csxuhuan.gelatoni.domain.model.converter.ResumeConverter;
import com.csxuhuan.gelatoni.domain.model.entity.Resume;
import com.csxuhuan.gelatoni.infrastructure.repository.ResumeRepository;
import com.csxuhuan.gelatoni.infrastructure.repository.entity.ResumeDO;
import com.csxuhuan.gelatoni.infrastructure.repository.mapper.ResumeMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 简历仓储实现
 *
 * @author csxuhuan
 */
@Repository
public class ResumeRepositoryImpl implements ResumeRepository {

    private final ResumeMapper resumeMapper;

    public ResumeRepositoryImpl(ResumeMapper resumeMapper) {
        this.resumeMapper = resumeMapper;
    }

    @Override
    public List<Resume> findAllOrderByVersionDesc() {
        List<ResumeDO> resumeDOList = resumeMapper.findAllOrderByVersionDesc();
        return resumeDOList.stream()
                .map(ResumeConverter::toEntity)
                .collect(Collectors.toList());
    }
}