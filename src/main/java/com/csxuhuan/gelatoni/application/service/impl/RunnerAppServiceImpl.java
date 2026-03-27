package com.csxuhuan.gelatoni.application.service.impl;

import com.csxuhuan.gelatoni.application.service.RunnerAppService;
import com.csxuhuan.gelatoni.domain.model.entity.Runner;
import com.csxuhuan.gelatoni.domain.query.RunnerPageQuery;
import com.csxuhuan.gelatoni.domain.result.PageResult;
import com.csxuhuan.gelatoni.domain.service.RunnerDomainService;
import org.springframework.stereotype.Service;

/**
 * Runner 应用服务实现
 */
@Service
public class RunnerAppServiceImpl implements RunnerAppService {

    private final RunnerDomainService runnerDomainService;

    public RunnerAppServiceImpl(RunnerDomainService runnerDomainService) {
        this.runnerDomainService = runnerDomainService;
    }

    @Override
    public PageResult<Runner> pageQuery(RunnerPageQuery query) {
        return runnerDomainService.pageQuery(query);
    }
}
