package com.csxuhuan.gelatoni.application.service;

import com.csxuhuan.gelatoni.domain.model.entity.Runner;
import com.csxuhuan.gelatoni.domain.query.RunnerPageQuery;
import com.csxuhuan.gelatoni.domain.result.PageResult;

/**
 * Runner 应用服务
 */
public interface RunnerAppService {

    /**
     * 分页查询选手
     */
    PageResult<Runner> pageQuery(RunnerPageQuery query);
}
