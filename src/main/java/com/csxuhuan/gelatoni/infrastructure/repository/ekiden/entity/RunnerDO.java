package com.csxuhuan.gelatoni.infrastructure.repository.ekiden.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

/**
 * RunnerDO
 *
 * 数据库表 runner 的直接映射对象
 * 只用于持久化层，不包含任何业务语义
 */
@TableName("runner")
public class RunnerDO {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 日文姓名
     */
    private String nameJa;

    /**
     * 中文姓名
     */
    private String nameZh;

    /**
     * 假名
     */
    private String nameKana;

    /**
     * 罗马音
     */
    private String nameRomaji;

    /**
     * 出生年份
     */
    private Integer birthYear;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;

    public RunnerDO() {
    }

    public RunnerDO(Long id, String nameJa, String nameZh, String nameKana, String nameRomaji, Integer birthYear, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.nameJa = nameJa;
        this.nameZh = nameZh;
        this.nameKana = nameKana;
        this.nameRomaji = nameRomaji;
        this.birthYear = birthYear;
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

    public String getNameJa() {
        return nameJa;
    }

    public void setNameJa(String nameJa) {
        this.nameJa = nameJa;
    }

    public String getNameZh() {
        return nameZh;
    }

    public void setNameZh(String nameZh) {
        this.nameZh = nameZh;
    }

    public String getNameKana() {
        return nameKana;
    }

    public void setNameKana(String nameKana) {
        this.nameKana = nameKana;
    }

    public String getNameRomaji() {
        return nameRomaji;
    }

    public void setNameRomaji(String nameRomaji) {
        this.nameRomaji = nameRomaji;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
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
