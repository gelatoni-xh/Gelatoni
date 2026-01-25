package com.csxuhuan.gelatoni.interfaces.web.request;

import javax.validation.constraints.NotBlank;

/**
 * 登录请求 DTO
 *
 * <p>用于接收前端登录请求，包含用户名和密码。
 *
 * @author csxuhuan
 */
public class LoginRequest {

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
