package com.csxuhuan.gelatoni.domain.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 用户实体类
 * 用于表示系统中的用户信息，包含用户ID和用户名
 */
@Data
@AllArgsConstructor
public class User {
    /**
     * 用户唯一标识ID
     */
    private Long id;

    /**
     * 用户姓名
     */
    private String name;
}

