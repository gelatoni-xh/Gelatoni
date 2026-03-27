package com.csxuhuan.gelatoni.domain.service;

import com.csxuhuan.gelatoni.domain.model.entity.Runner;
import com.csxuhuan.gelatoni.domain.query.RunnerPageQuery;

import java.util.List;

/**
 * Runner 领域服务
 */
public interface RunnerDomainService {

    /**
     * 分页查询选手
     */
    List<Runner> getRunnerPage(RunnerPageQuery query);

    /**
     * 查询总数
     */
    long count();
}
