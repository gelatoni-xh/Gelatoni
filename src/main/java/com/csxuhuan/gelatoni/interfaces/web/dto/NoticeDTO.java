package com.csxuhuan.gelatoni.interfaces.web.dto;

import java.time.LocalDateTime;

/**
 * 公告数据传输对象（DTO）
 *
 * <p>用于向前端返回公告信息。DTO 只包含前端需要展示的字段，
 * 屏蔽了领域模型的内部细节（如 ID、删除标记等）。
 *
 * <p>字段说明：
 * <ul>
 *     <li>title - 公告标题</li>
 *     <li>content - 公告内容（支持 Markdown 格式）</li>
 *     <li>createTime - 创建时间</li>
 * </ul>
 *
 * @author csxuhuan
 */
public class NoticeDTO {

    /** 公告标题 */
    private String title;

    /** 公告内容，支持 Markdown 格式 */
    private String content;

    /** 创建时间 */
    private LocalDateTime createTime;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}
