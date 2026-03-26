package com.csxuhuan.gelatoni.infrastructure.repository.ekiden.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

/**
 * RaceResultDO
 *
 * 数据库表 race_result 的直接映射对象
 * 只用于持久化层，不包含任何业务语义
 */
@TableName("race_result")
public class RaceResultDO {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 比赛ID
     */
    private Long raceId;

    /**
     * 选手ID
     */
    private Long runnerId;

    /**
     * 所属大学ID
     */
    private Long teamId;

    /**
     * 区间（1-10）
     */
    private Integer section;

    /**
     * 区间成绩（如1:02:35）
     */
    private String finishTime;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;

    public RaceResultDO() {
    }

    public RaceResultDO(Long id, Long raceId, Long runnerId, Long teamId, Integer section, String finishTime, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.raceId = raceId;
        this.runnerId = runnerId;
        this.teamId = teamId;
        this.section = section;
        this.finishTime = finishTime;
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

    public Long getRaceId() {
        return raceId;
    }

    public void setRaceId(Long raceId) {
        this.raceId = raceId;
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

    public Integer getSection() {
        return section;
    }

    public void setSection(Integer section) {
        this.section = section;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
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
