package com.csxuhuan.gelatoni.interfaces.web.common;

/**
 * 权限编码常量类
 *
 * <p>定义系统中所有权限编码的常量，用于 {@link com.csxuhuan.gelatoni.interfaces.config.AuthCheck} 注解。
 * 统一管理权限编码，避免硬编码字符串，提高代码可维护性。
 *
 * <p>使用示例：
 * <pre>
 * &#64;AuthCheck(permissionCode = PermissionConstants.PERM_NOTICE_VIEW)
 * public BaseResponse&lt;PageData&lt;NoticeDTO&gt;&gt; page(...) { ... }
 * </pre>
 *
 * @author csxuhuan
 * @see com.csxuhuan.gelatoni.interfaces.config.AuthCheck
 */
public class PermissionConstants {

    /**
     * 查看公告权限
     */
    public static final String PERM_NOTICE_VIEW = "PERM_NOTICE_VIEW";

    /**
     * 创建公告权限
     */
    public static final String PERM_NOTICE_CREATE = "PERM_NOTICE_CREATE";

    /**
     * TODO 权限（用于所有 TODO 相关的操作）
     */
    public static final String PERM_TODO = "PERM_TODO";

    /**
     * 私有构造函数，防止实例化
     */
    private PermissionConstants() {
        throw new UnsupportedOperationException("常量类不能被实例化");
    }
}
