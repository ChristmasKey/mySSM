package com.stone.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user")
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
    @TableField(value = "pwd", select = false)
    private String password;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 手机号码
     */
    private String tel;

    /**
     * 邮箱
     */
    @TableField(exist = false)
    private String email;
}
