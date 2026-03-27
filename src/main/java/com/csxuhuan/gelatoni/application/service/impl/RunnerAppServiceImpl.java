package com.csxuhuan.gelatoni.application.service.impl;

import com.csxuhuan.gelatoni.application.assembler.RunnerAssembler;
import com.csxuhuan.gelatoni.application.dto.RunnerDTO;
import com.csxuhuan.gelatoni.application.service.RunnerAppService;
import com.csxuhuan.gelatoni.domain.model.entity.Runner;
import com.csxuhuan.gelatoni.domain.query.RunnerPageQuery;
import com.csxuhuan.gelatoni.domain.service.RunnerDomainService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Runner 应用服务实现
 */
@Service
public class RunnerAppServiceImpl implements RunnerAppService {

    private final RunnerDomainService runnerDomainService;
    private final RunnerAssembler assembler = new RunnerAssembler();

    public RunnerAppServiceImpl(RunnerDomainService runnerDomainService) {
        this.runnerDomainService = runnerDomainService;
    }

    @Override
    public List<RunnerDTO> getRunnerPage(Integer pageNum, Integer pageSize) {
        RunnerPageQuery query = new RunnerPageQuery(pageNum, pageSize);
        List<Runner> runners = runnerDomainService.getRunnerPage(query);
        return assembler.toDTOList(runners);
    }

    @Override
    public long count() {
        return runnerDomainService.count();
    }
}
