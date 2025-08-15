package com.stone.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

// 创建SpringMVC的配置类，加载controller包下对应的Bean
@Configuration
@ComponentScan(basePackages = "com.stone.springmvc.controller")
public class SpringMvcConfig {
}
