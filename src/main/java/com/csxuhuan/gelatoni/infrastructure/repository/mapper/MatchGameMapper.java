package com.csxuhuan.gelatoni.infrastructure.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.csxuhuan.gelatoni.infrastructure.repository.entity.MatchGameDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * MatchGameMapper
 * MyBatis-Plus Mapper，提供 CRUD 接口
 *
 * @author Gelatoni
 */
@Mapper
public interface MatchGameMapper extends BaseMapper<MatchGameDO> {
}