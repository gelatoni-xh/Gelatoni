package com.csxuhuan.gelatoni.infrastructure.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.csxuhuan.gelatoni.infrastructure.repository.entity.TodoTagDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * TodoTagMapper
 * MyBatis-Plus Mapper，提供 CRUD 接口
 */
@Mapper
public interface TodoTagMapper extends BaseMapper<TodoTagDO> {
}
