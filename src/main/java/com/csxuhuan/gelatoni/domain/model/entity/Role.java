package com.csxuhuan.gelatoni.domain.model.entity;

import java.time.LocalDateTime;

/**
 * 角色领域实体
 *
 * <p>代表一个"有效的角色"，是领域模型的核心对象。
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
public class Role {

    /** 角色唯一标识 */
    private final Long id;

    /** 角色编码 */
    private final String roleCode;

    /** 角色名称 */
    private final String roleName;

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
     * 构造角色领域对象
     *
     * @param id           角色 ID，新建时可为 null
     * @param roleCode     角色编码
     * @param roleName     角色名称
     * @param status       状态：1-启用 0-禁用
     * @param creator      创建人
     * @param modifier     修改人
     * @param createTime   创建时间，新建时可为 null（由数据库填充）
     * @param modifiedTime 修改时间，新建时可为 null（由数据库填充）
     */
    public Role(Long id,
                String roleCode,
                String roleName,
                Byte status,
                Long creator,
                Long modifier,
                LocalDateTime createTime,
                LocalDateTime modifiedTime) {
        this.id = id;
        this.roleCode = roleCode;
        this.roleName = roleName;
        this.status = status;
        this.creator = creator;
        this.modifier = modifier;
        this.createTime = createTime;
        this.modifiedTime = modifiedTime;
    }

    public Long getId() {
        return id;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public String getRoleName() {
        return roleName;
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
