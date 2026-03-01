package com.csxuhuan.gelatoni.domain.service;

import com.csxuhuan.gelatoni.domain.model.entity.Resume;

import java.util.List;

/**
 * 简历领域服务接口
 *
 * @author csxuhuan
 */
public interface ResumeDomainService {

    /**
     * 查询所有简历列表
     * 按版本号倒序排列
     *
     * @return 简历列表
     */
    List<Resume> findAllResumes();
}