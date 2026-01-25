package com.csxuhuan.gelatoni.interfaces.web.common;

import java.util.List;

/**
 * 用户信息持有器
 *
 * <p>使用 ThreadLocal 存储当前请求线程的用户信息，包括：
 * <ul>
 *     <li>用户基本信息</li>
 *     <li>用户拥有的所有角色编码列表</li>
 *     <li>用户拥有的所有权限编码列表</li>
 * </ul>
 *
 * <p>使用示例：
 * <pre>
 * // 在拦截器中设置用户信息
 * UserHolder.set(userId, username, roleCodes, permissionCodes);
 *
 * // 在业务代码中获取用户信息
 * Long userId = UserHolder.getUserId();
 * String username = UserHolder.getUsername();
 * List&lt;String&gt; roleCodes = UserHolder.getRoleCodes();
 * List&lt;String&gt; permissionCodes = UserHolder.getPermissionCodes();
 *
 * // 在拦截器 afterCompletion 中清理
 * UserHolder.clear();
 * </pre>
 *
 * <p>注意：此类使用 ThreadLocal 存储数据，每个请求线程独立存储，
 * 请求结束后必须调用 clear() 方法清理，避免内存泄漏。
 *
 * @author csxuhuan
 */
public class UserHolder {

    /**
     * 用户ID ThreadLocal
     */
    private static final ThreadLocal<Long> userIdHolder = new ThreadLocal<>();

    /**
     * 用户名 ThreadLocal
     */
    private static final ThreadLocal<String> usernameHolder = new ThreadLocal<>();

    /**
     * 角色编码列表 ThreadLocal
     */
    private static final ThreadLocal<List<String>> roleCodesHolder = new ThreadLocal<>();

    /**
     * 权限编码列表 ThreadLocal
     */
    private static final ThreadLocal<List<String>> permissionCodesHolder = new ThreadLocal<>();

    /**
     * 设置当前线程的用户信息
     *
     * @param userId           用户ID
     * @param username         用户名
     * @param roleCodes        角色编码列表
     * @param permissionCodes  权限编码列表
     */
    public static void set(Long userId, String username, List<String> roleCodes, List<String> permissionCodes) {
        userIdHolder.set(userId);
        usernameHolder.set(username);
        roleCodesHolder.set(roleCodes);
        permissionCodesHolder.set(permissionCodes);
    }

    /**
     * 获取当前线程的用户ID
     *
     * @return 用户ID，如果未设置则返回 null
     */
    public static Long getUserId() {
        return userIdHolder.get();
    }

    /**
     * 获取当前线程的用户名
     *
     * @return 用户名，如果未设置则返回 null
     */
    public static String getUsername() {
        return usernameHolder.get();
    }

    /**
     * 获取当前线程的角色编码列表
     *
     * @return 角色编码列表，如果未设置则返回 null
     */
    public static List<String> getRoleCodes() {
        return roleCodesHolder.get();
    }

    /**
     * 获取当前线程的权限编码列表
     *
     * @return 权限编码列表，如果未设置则返回 null
     */
    public static List<String> getPermissionCodes() {
        return permissionCodesHolder.get();
    }

    /**
     * 检查当前线程是否包含指定的权限编码
     *
     * @param permissionCode 权限编码
     * @return true 如果包含该权限，false 否则
     */
    public static boolean hasPermission(String permissionCode) {
        List<String> permissionCodes = permissionCodesHolder.get();
        return permissionCodes != null && permissionCodes.contains(permissionCode);
    }

    /**
     * 检查当前线程是否包含指定的角色编码
     *
     * @param roleCode 角色编码
     * @return true 如果包含该角色，false 否则
     */
    public static boolean hasRole(String roleCode) {
        List<String> roleCodes = roleCodesHolder.get();
        return roleCodes != null && roleCodes.contains(roleCode);
    }

    /**
     * 清理当前线程的所有用户信息
     *
     * <p>必须在请求处理完成后调用，避免内存泄漏。
     * 通常在拦截器的 afterCompletion 方法中调用。
     */
    public static void clear() {
        userIdHolder.remove();
        usernameHolder.remove();
        roleCodesHolder.remove();
        permissionCodesHolder.remove();
    }
}
