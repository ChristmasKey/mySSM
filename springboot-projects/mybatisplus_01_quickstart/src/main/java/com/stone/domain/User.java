package com.stone.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

/**
 * user 表对应的实体类（Domain/POJO）
 */
public class User {
    /**
     * 主键ID，自增
     * 使用MyBatisPlus注解指定为自增主键
     */
    @TableId(type = IdType.AUTO)
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

    // getter 方法
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public Integer getAge() {
        return age;
    }

    public String getTel() {
        return tel;
    }

    // setter 方法
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    // 可选：重写 toString 方法
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", tel='" + tel + '\'' +
                '}';
    }
}
