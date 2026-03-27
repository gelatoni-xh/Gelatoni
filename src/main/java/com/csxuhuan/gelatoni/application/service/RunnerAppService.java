package com.csxuhuan.gelatoni.application.service;

import com.csxuhuan.gelatoni.application.dto.RunnerDTO;

import java.util.List;

/**
 * Runner 应用服务
 */
public interface RunnerAppService {

    /**
     * 分页查询选手
     */
    List<RunnerDTO> getRunnerPage(Integer pageNum, Integer pageSize);

    /**
     * 查询总数
     */
    long count();
}
