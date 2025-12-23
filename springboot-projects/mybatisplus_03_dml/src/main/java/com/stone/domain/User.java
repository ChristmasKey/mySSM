package com.stone.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
@TableName("user")
public class User {
    // 指定ID生成策略
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private Integer age;
    private String password;
    private String tel;

    // @TableLogic(value = "0", delval = "1")
    private Integer deleted;

    @Version
    private Integer version;
}
