package com.csxuhuan.gelatoni.infrastructure.repository.ekiden.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

/**
 * RunnerCareerDO
 *
 * 数据库表 runner_career 的直接映射对象
 * 只用于持久化层，不包含任何业务语义
 */
@TableName("runner_career")
public class RunnerCareerDO {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 选手ID
     */
    private Long runnerId;

    /**
     * 队伍ID
     */
    private Long teamId;

    /**
     * 履历顺序：1=高中,2=大学,3=实业团
     */
    private Integer careerOrder;

    /**
     * 入学/入团年份
     */
    private Integer startYear;

    /**
     * 毕业/离团年份
     */
    private Integer endYear;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;

    public RunnerCareerDO() {
    }

    public RunnerCareerDO(Long id, Long runnerId, Long teamId, Integer careerOrder, Integer startYear, Integer endYear, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.runnerId = runnerId;
        this.teamId = teamId;
        this.careerOrder = careerOrder;
        this.startYear = startYear;
        this.endYear = endYear;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // ===== Getter / Setter =====

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRunnerId() {
        return runnerId;
    }

    public void setRunnerId(Long runnerId) {
        this.runnerId = runnerId;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public Integer getCareerOrder() {
        return careerOrder;
    }

    public void setCareerOrder(Integer careerOrder) {
        this.careerOrder = careerOrder;
    }

    public Integer getStartYear() {
        return startYear;
    }

    public void setStartYear(Integer startYear) {
        this.startYear = startYear;
    }

    public Integer getEndYear() {
        return endYear;
    }

    public void setEndYear(Integer endYear) {
        this.endYear = endYear;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
