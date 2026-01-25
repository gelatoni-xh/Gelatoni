package com.csxuhuan.gelatoni.infrastructure.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 密码工具类
 */
@Component
public class PasswordUtil {

    private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    /**
     * 编码原始密码
     * @param rawPassword 原始密码
     * @return 编码后的密码
     */
    public static String encodePassword(String rawPassword) {
        return PASSWORD_ENCODER.encode(rawPassword);
    }

    /**
     * 验证密码
     * @param rawPassword 原始密码
     * @param encodedPassword 编码后的密码
     * @return 验证结果
     */
    public static boolean verifyPassword(String rawPassword, String encodedPassword) {
        return PASSWORD_ENCODER.matches(rawPassword, encodedPassword);
    }

    public static void main(String[] args) {
        System.out.println(encodePassword(""));
    }
}