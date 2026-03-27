package com.csxuhuan.gelatoni.domain.service.impl;

import com.csxuhuan.gelatoni.domain.model.entity.Runner;
import com.csxuhuan.gelatoni.domain.query.RunnerPageQuery;
import com.csxuhuan.gelatoni.domain.result.PageResult;
import com.csxuhuan.gelatoni.domain.service.RunnerDomainService;
import com.csxuhuan.gelatoni.infrastructure.repository.ekiden.RunnerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Runner 领域服务实现
 */
@Service
public class RunnerDomainServiceImpl implements RunnerDomainService {

    private final RunnerRepository runnerRepository;

    public RunnerDomainServiceImpl(RunnerRepository runnerRepository) {
        this.runnerRepository = runnerRepository;
    }

    @Override
    public PageResult<Runner> pageQuery(RunnerPageQuery query) {
        List<Runner> records = runnerRepository.findPage(query);
        long total = runnerRepository.count();
        return new PageResult<>(records, query.getPageNo(), query.getPageSize(), total);
    }
}
