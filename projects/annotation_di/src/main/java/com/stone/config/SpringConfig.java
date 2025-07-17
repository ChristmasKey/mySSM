package com.stone.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = "com.stone")
@PropertySource({"classpath:jdbc.properties"}) // 加载项目路径下的properties文件
public class SpringConfig {
}
