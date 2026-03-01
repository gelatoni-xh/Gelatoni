package com.csxuhuan.gelatoni.infrastructure.repository.mapper;

import com.csxuhuan.gelatoni.infrastructure.repository.entity.ResumeDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 简历Mapper
 *
 * @author csxuhuan
 */
@Mapper
public interface ResumeMapper {

    /**
     * 查询所有简历，按版本号倒序排列
     *
     * @return 简历列表
     */
    @Select("SELECT id, version, name, resume_data, deleted, create_time, update_time " +
            "FROM resume " +
            "WHERE deleted = 0 " +
            "ORDER BY version DESC")
    List<ResumeDO> findAllOrderByVersionDesc();
}