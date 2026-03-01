package com.csxuhuan.gelatoni.application.service;

import com.csxuhuan.gelatoni.application.dto.ResumeDTO;

import java.util.List;

/**
 * 简历应用服务接口
 *
 * @author csxuhuan
 */
public interface ResumeAppService {

    /**
     * 查询所有简历列表
     * 按版本号倒序排列
     *
     * @return 简历列表
     */
    List<ResumeDTO> findAllResumes();
}