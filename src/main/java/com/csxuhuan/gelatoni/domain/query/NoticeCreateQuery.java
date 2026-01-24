package com.csxuhuan.gelatoni.domain.query;

import com.csxuhuan.gelatoni.domain.model.entity.Notice;

/**
 * 公告创建查询对象
 *
 * <p>封装创建公告所需的参数。Query 对象是领域层的输入参数，
 * 用于传递业务操作所需的数据。
 *
 * <p>与 Request 的区别：
 * <ul>
 *     <li>Request - interfaces 层对象，面向 HTTP 协议，包含校验注解</li>
 *     <li>Query - domain 层对象，面向业务逻辑，可包含工厂方法</li>
 * </ul>
 *
 * @author csxuhuan
 */
public class NoticeCreateQuery {

    /** 公告标题 */
    private String title;

    /** 公告内容，支持 Markdown 格式 */
    private String content;

    /**
     * 构造公告创建查询对象
     *
     * @param title   公告标题
     * @param content 公告内容
     */
    public NoticeCreateQuery(String title, String content) {
        this.title = title;
        this.content = content;
    }

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

    /**
     * 转换为公告领域实体
     *
     * <p>工厂方法，将查询对象转换为领域实体。
     * ID、创建时间、修改时间设为 null，由数据库自动填充。
     *
     * @return 新建的公告领域实体（未持久化）
     */
    public Notice toNotice() {
        return new Notice(null, title, content, null, null);
    }
}
