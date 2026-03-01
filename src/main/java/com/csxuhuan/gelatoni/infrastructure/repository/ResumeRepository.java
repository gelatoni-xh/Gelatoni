package com.csxuhuan.gelatoni.infrastructure.repository;

import com.csxuhuan.gelatoni.domain.model.entity.Resume;

import java.util.List;

/**
 * 简历仓储接口
 *
 * @author csxuhuan
 */
public interface ResumeRepository {

    /**
     * 查询所有简历，按版本号倒序排列
     *
     * @return 简历列表
     */
    List<Resume> findAllOrderByVersionDesc();
}