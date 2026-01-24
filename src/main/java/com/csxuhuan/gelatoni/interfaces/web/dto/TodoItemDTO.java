package com.csxuhuan.gelatoni.interfaces.web.dto;

import java.time.LocalDateTime;

/**
 * TODO 项数据传输对象（DTO）
 *
 * <p>用于向前端返回 TODO 项信息。除了 TODO 项本身的属性外，
 * 还包含关联的标签名称，避免前端需要额外请求获取标签信息。
 *
 * <p>字段说明：
 * <ul>
 *     <li>id - TODO 项唯一标识</li>
 *     <li>content - TODO 内容</li>
 *     <li>completed - 完成状态</li>
 *     <li>tagId - 关联的标签 ID</li>
 *     <li>tagName - 关联的标签名称（冗余字段，便于前端展示）</li>
 *     <li>createTime - 创建时间</li>
 *     <li>modifiedTime - 最后修改时间</li>
 * </ul>
 *
 * @author csxuhuan
 */
public class TodoItemDTO {

    /** TODO 项唯一标识 */
    private Long id;

    /** TODO 内容 */
    private String content;

    /** 是否已完成 */
    private Boolean completed;

    /** 关联的标签 ID，可为 null */
    private Long tagId;

    /** 关联的标签名称，冗余字段便于前端展示 */
    private String tagName;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
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
