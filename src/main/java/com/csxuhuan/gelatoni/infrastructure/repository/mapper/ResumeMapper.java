package com.csxuhuan.gelatoni.infrastructure.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.csxuhuan.gelatoni.infrastructure.repository.entity.ResumeDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 简历Mapper
 * MyBatis-Plus Mapper，提供 CRUD 接口
 *
 * @author csxuhuan
 */
@Mapper
public interface ResumeMapper extends BaseMapper<ResumeDO> {
}