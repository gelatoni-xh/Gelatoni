package com.csxuhuan.gelatoni.infrastructure.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.csxuhuan.gelatoni.infrastructure.repository.entity.MatchPlayerStatsDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * MatchPlayerStatsMapper
 * MyBatis-Plus Mapper，提供 CRUD 接口
 *
 * @author Gelatoni
 */
@Mapper
public interface MatchPlayerStatsMapper extends BaseMapper<MatchPlayerStatsDO> {
}