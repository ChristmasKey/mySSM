package com.stone.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = {"com.stone"})
@PropertySource({"classpath:jdbc.properties"})
@Import(JdbcConfig.class)
public class SpringConfig {
}
