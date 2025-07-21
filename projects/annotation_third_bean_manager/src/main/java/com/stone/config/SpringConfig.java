package com.stone.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({JdbcConfig.class})
@ComponentScan(basePackages = {"com.stone"})
public class SpringConfig {
}
