package com.csxuhuan.gelatoni.domain.model.entity;

import java.time.LocalDateTime;

/**
 * 公告领域实体
 *
 * <p>代表一个"有效的公告"，是领域模型的核心对象。
 * 领域实体只关注业务属性，不关心：
 * <ul>
 *     <li>删除状态（已删除的数据不应该转换为领域对象）</li>
 *     <li>数据库字段映射</li>
 *     <li>序列化/反序列化</li>
 * </ul>
 *
 * <p>设计原则：
 * <ul>
 *     <li>不可变对象（Immutable）：所有字段为 final，通过构造函数初始化</li>
 *     <li>只暴露 getter，不提供 setter</li>
 *     <li>业务行为方法返回新对象而非修改自身</li>
 * </ul>
 *
 * @author csxuhuan
 */
public class Notice {

    /** 公告唯一标识 */
    private final Long id;

    /** 公告标题 */
    private final String title;

    /** 公告内容，支持 Markdown 格式 */
    private final String content;

    /** 创建时间 */
    private final LocalDateTime createTime;

    /** 最后修改时间 */
    private final LocalDateTime modifiedTime;

    /**
     * 构造公告领域对象
     *
     * @param id           公告 ID，新建时可为 null
     * @param title        公告标题
     * @param content      公告内容
     * @param createTime   创建时间，新建时可为 null（由数据库填充）
     * @param modifiedTime 修改时间，新建时可为 null（由数据库填充）
     */
    public Notice(Long id,
                  String title,
                  String content,
                  LocalDateTime createTime,
                  LocalDateTime modifiedTime) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createTime = createTime;
        this.modifiedTime = modifiedTime;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public LocalDateTime getModifiedTime() {
        return modifiedTime;
    }
}
