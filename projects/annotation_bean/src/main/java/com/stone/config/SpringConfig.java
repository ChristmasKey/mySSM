package com.stone.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration // 声明这是一个Spring配置类
@ComponentScan(basePackages = {"com.stone"}) // 配置注解扫描包，配置多个包用逗号隔开
public class SpringConfig {
}
