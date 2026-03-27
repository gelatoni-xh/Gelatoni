package com.csxuhuan.gelatoni.infrastructure.repository.ekiden.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.csxuhuan.gelatoni.domain.model.converter.RunnerConverter;
import com.csxuhuan.gelatoni.domain.model.entity.Runner;
import com.csxuhuan.gelatoni.domain.query.RunnerPageQuery;
import com.csxuhuan.gelatoni.infrastructure.repository.ekiden.RunnerRepository;
import com.csxuhuan.gelatoni.infrastructure.repository.ekiden.entity.RunnerDO;
import com.csxuhuan.gelatoni.infrastructure.repository.ekiden.mapper.RunnerMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Runner 仓储实现
 */
@Repository
public class RunnerRepositoryImpl implements RunnerRepository {

    private final RunnerMapper runnerMapper;

    public RunnerRepositoryImpl(RunnerMapper runnerMapper) {
        this.runnerMapper = runnerMapper;
    }

    @Override
    public List<Runner> findPage(RunnerPageQuery query) {
        LambdaQueryWrapper<RunnerDO> wrapper = Wrappers.lambdaQuery();
        wrapper.orderByDesc(RunnerDO::getCreatedAt);

        int offset = (query.getPageNo() - 1) * query.getPageSize();
        wrapper.last("LIMIT " + offset + ", " + query.getPageSize());

        List<RunnerDO> runnerDOList = runnerMapper.selectList(wrapper);
        return runnerDOList.stream()
                .map(RunnerConverter::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public long count() {
        LambdaQueryWrapper<RunnerDO> wrapper = Wrappers.lambdaQuery();
        return runnerMapper.selectCount(wrapper);
    }
}
