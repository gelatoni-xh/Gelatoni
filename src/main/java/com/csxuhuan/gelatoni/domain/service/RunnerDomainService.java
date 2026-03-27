package com.csxuhuan.gelatoni.domain.service;

import com.csxuhuan.gelatoni.domain.query.RunnerPageQuery;
import com.csxuhuan.gelatoni.domain.result.PageResult;
import com.csxuhuan.gelatoni.domain.model.entity.Runner;

/**
 * Runner 领域服务
 */
public interface RunnerDomainService {

    /**
     * 分页查询选手
     */
    PageResult<Runner> pageQuery(RunnerPageQuery query);
}
