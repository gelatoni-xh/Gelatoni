package com.csxuhuan.gelatoni.interfaces.config;

import java.lang.annotation.*;

/**
 * 认证检查注解
 *
 * <p>标记需要进行身份验证和权限检查的 Controller 方法。
 * 被此注解标记的方法在调用前会经过 {@link AuthInterceptor} 拦截器的验证，
 * 需要验证 Token 的有效性以及用户是否拥有指定的权限。
 *
 * <p>权限检查规则：
 * <ul>
 *     <li>如果指定了 permissionCode，会检查用户是否拥有该权限</li>
 *     <li>如果该权限对应的角色是未登录权限（匿名用户权限），则可以不传 Token 直接放行</li>
 *     <li>如果未指定 permissionCode，则只进行 Token 验证</li>
 * </ul>
 *
 * <p>使用示例：
 * <pre>
 * // 只进行 Token 验证
 * &#64;AuthCheck
 * &#64;PostMapping("/create")
 * public BaseResponse&lt;Integer&gt; create(...) { ... }
 *
 * // 验证 Token 并检查权限
 * &#64;AuthCheck(permissionCode = "user:create")
 * &#64;PostMapping("/create")
 * public BaseResponse&lt;Integer&gt; create(...) { ... }
 * </pre>
 *
 * <p>验证失败时返回 HTTP 401 Unauthorized 响应。
 *
 * @author csxuhuan
 * @see AuthInterceptor 认证拦截器实现
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthCheck {
    /**
     * 权限编码
     *
     * <p>如果指定了权限编码，会检查用户是否拥有该权限。
     * 如果该权限对应的角色是未登录权限（匿名用户权限），则可以不传 Token 直接放行。
     *
     * @return 权限编码，默认为空字符串（只进行 Token 验证）
     */
    String permissionCode() default "";
}
