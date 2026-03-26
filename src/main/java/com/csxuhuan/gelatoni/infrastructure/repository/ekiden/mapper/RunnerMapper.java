package com.csxuhuan.gelatoni.infrastructure.repository.ekiden.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.csxuhuan.gelatoni.infrastructure.repository.ekiden.entity.RunnerDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * RunnerMapper
 * MyBatis-Plus Mapper，提供 CRUD 接口
 */
@Mapper
public interface RunnerMapper extends BaseMapper<RunnerDO> {
}
