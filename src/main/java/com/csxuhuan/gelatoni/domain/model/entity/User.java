package com.csxuhuan.gelatoni.domain.model.entity;

import java.time.LocalDateTime;

/**
 * 用户领域实体
 *
 * <p>代表一个"有效的用户"，是领域模型的核心对象。
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
public class User {

    /** 用户唯一标识 */
    private final Long id;

    /** 用户名 */
    private final String username;

    /** 密码哈希 */
    private final String passwordHash;

    /** 用户昵称 */
    private final String nickname;

    /** 状态：1-启用 0-禁用 */
    private final Byte status;

    /** 创建人 */
    private final Long creator;

    /** 修改人 */
    private final Long modifier;

    /** 创建时间 */
    private final LocalDateTime createTime;

    /** 最后修改时间 */
    private final LocalDateTime modifiedTime;

    /**
     * 构造用户领域对象
     *
     * @param id           用户 ID，新建时可为 null
     * @param username     用户名
     * @param passwordHash 密码哈希
     * @param nickname     用户昵称
     * @param status       状态：1-启用 0-禁用
     * @param creator      创建人
     * @param modifier     修改人
     * @param createTime   创建时间，新建时可为 null（由数据库填充）
     * @param modifiedTime 修改时间，新建时可为 null（由数据库填充）
     */
    public User(Long id,
                String username,
                String passwordHash,
                String nickname,
                Byte status,
                Long creator,
                Long modifier,
                LocalDateTime createTime,
                LocalDateTime modifiedTime) {
        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
        this.nickname = nickname;
        this.status = status;
        this.creator = creator;
        this.modifier = modifier;
        this.createTime = createTime;
        this.modifiedTime = modifiedTime;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getNickname() {
        return nickname;
    }

    public Byte getStatus() {
        return status;
    }

    public Long getCreator() {
        return creator;
    }

    public Long getModifier() {
        return modifier;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public LocalDateTime getModifiedTime() {
        return modifiedTime;
    }
}
