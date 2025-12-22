package com.stone.domain;

import lombok.Data;

@Data
public class User {
    /**
     * 主键
     */
    private Long id;

    /**
     * 用户名
     */
    private String name;

    /**
     * 密码（通常为加密存储）
     */
    private String password;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 手机号码
     */
    private String tel;
}
