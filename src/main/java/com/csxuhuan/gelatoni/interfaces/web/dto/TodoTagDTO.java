package com.csxuhuan.gelatoni.interfaces.web.dto;

import java.time.LocalDateTime;

/**
 * TODO 标签数据传输对象（DTO）
 *
 * <p>用于向前端返回标签信息。标签用于对 TODO 项进行分类管理。
 *
 * <p>字段说明：
 * <ul>
 *     <li>id - 标签唯一标识</li>
 *     <li>name - 标签名称</li>
 *     <li>createTime - 创建时间</li>
 * </ul>
 *
 * @author csxuhuan
 */
public class TodoTagDTO {

    /** 标签唯一标识 */
    private Long id;

    /** 标签名称 */
    private String name;

    /** 创建时间 */
    private LocalDateTime createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}
