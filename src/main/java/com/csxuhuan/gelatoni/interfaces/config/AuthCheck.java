package com.csxuhuan.gelatoni.interfaces.config;

import java.lang.annotation.*;

/**
 * 认证检查注解
 *
 * <p>标记需要进行身份验证的 Controller 方法。
 * 被此注解标记的方法在调用前会经过 {@link AuthInterceptor} 拦截器的验证，
 * 需要在请求头中携带有效的 Bearer Token。
 *
 * <p>使用示例：
 * <pre>
 * &#64;AuthCheck
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
}
