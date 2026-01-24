package com.csxuhuan.gelatoni.infrastructure.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.csxuhuan.gelatoni.infrastructure.repository.entity.PermissionDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * PermissionDOMapper
 * MyBatis-Plus Mapper，提供 CRUD 接口
 */
@Mapper
public interface PermissionMapper extends BaseMapper<PermissionDO> {
}
