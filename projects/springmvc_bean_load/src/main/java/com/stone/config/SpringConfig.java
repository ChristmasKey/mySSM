package com.stone.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;

@Configuration
@ComponentScan(basePackages = "com.stone", excludeFilters = @ComponentScan.Filter(// 方式一：配置过滤器
        type = FilterType.ANNOTATION,// 指定过滤策略：按注解过滤
        classes = Controller.class // 指定注解类：Controller注解类
))
// @ComponentScan(basePackages = {"com.stone.service", "com.stone.dao"}) // 方式二：精准扫描包
public class SpringConfig {
}
