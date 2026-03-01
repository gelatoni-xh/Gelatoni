package com.csxuhuan.gelatoni.infrastructure.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.csxuhuan.gelatoni.domain.model.converter.ResumeConverter;
import com.csxuhuan.gelatoni.domain.model.entity.Resume;
import com.csxuhuan.gelatoni.infrastructure.repository.ResumeRepository;
import com.csxuhuan.gelatoni.infrastructure.repository.entity.ResumeDO;
import com.csxuhuan.gelatoni.infrastructure.repository.mapper.ResumeMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 简历仓储实现
 *
 * <p>实现 {@link ResumeRepository} 接口，使用 MyBatis-Plus 进行数据访问。
 *
 * @author csxuhuan
 */
@Repository
public class ResumeRepositoryImpl implements ResumeRepository {

    private final ResumeMapper resumeMapper;

    public ResumeRepositoryImpl(ResumeMapper resumeMapper) {
        this.resumeMapper = resumeMapper;
    }

    @Override
    public List<Resume> findAllOrderByVersionDesc() {
        // 使用 MyBatis-Plus 的 LambdaQueryWrapper 构建查询条件
        LambdaQueryWrapper<ResumeDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ResumeDO::getDeleted, 0)  // 只查询未删除的记录
                   .orderByDesc(ResumeDO::getVersion);  // 按版本号倒序排列
        
        List<ResumeDO> resumeDOList = resumeMapper.selectList(queryWrapper);
        return resumeDOList.stream()
                .map(ResumeConverter::toEntity)
                .collect(Collectors.toList());
    }
}