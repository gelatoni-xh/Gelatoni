package com.csxuhuan.gelatoni.infrastructure.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.csxuhuan.gelatoni.infrastructure.repository.entity.UserDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * UserDOMapper
 * MyBatis-Plus Mapper，提供 CRUD 接口
 */
@Mapper
public interface UserMapper extends BaseMapper<UserDO> {
}
