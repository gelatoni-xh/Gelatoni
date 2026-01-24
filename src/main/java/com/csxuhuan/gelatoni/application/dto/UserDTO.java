package com.csxuhuan.gelatoni.application.dto;

import java.time.LocalDateTime;

/**
 * 用户数据传输对象（DTO）
 *
 * <p>用于向前端返回用户信息。DTO 只包含前端需要展示的字段，
 * 屏蔽了领域模型的内部细节（如密码哈希、创建人、修改人等）。
 *
 * <p>字段说明：
 * <ul>
 *     <li>id - 用户唯一标识</li>
 *     <li>username - 用户名</li>
 *     <li>nickname - 用户昵称</li>
 *     <li>status - 状态：1-启用 0-禁用</li>
 *     <li>createTime - 创建时间</li>
 *     <li>modifiedTime - 最后修改时间</li>
 * </ul>
 *
 * @author csxuhuan
 */
public class UserDTO {

    /** 用户唯一标识 */
    private Long id;

    /** 用户名 */
    private String username;

    /** 用户昵称 */
    private String nickname;

    /** 状态：1-启用 0-禁用 */
    private Byte status;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 最后修改时间 */
    private LocalDateTime modifiedTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(LocalDateTime modifiedTime) {
        this.modifiedTime = modifiedTime;
    }
}
