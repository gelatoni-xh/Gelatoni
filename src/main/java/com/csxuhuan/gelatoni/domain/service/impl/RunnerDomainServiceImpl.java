package com.csxuhuan.gelatoni.domain.service.impl;

import com.csxuhuan.gelatoni.domain.model.entity.Runner;
import com.csxuhuan.gelatoni.domain.query.RunnerPageQuery;
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
    public List<Runner> getRunnerPage(RunnerPageQuery query) {
        return runnerRepository.findPage(query);
    }

    @Override
    public long count() {
        return runnerRepository.count();
    }
}
