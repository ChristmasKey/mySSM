package com.stone.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan(basePackages = "com.stone.controller")
@EnableWebMvc // 通过这个注解可以开启JSON格式数据的支持
public class SpringMvcConfig {
}
