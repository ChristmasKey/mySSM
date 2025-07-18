package com.stone.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

public class JdbcConfig {

    // 1.定义一个方法获得要管理的对象
    // 2.通过@Bean注解将方法的返回值注册成Bean对象
    @Bean
    public DataSource dataSource() {
        DruidDataSource ds = new DruidDataSource();
        ds.setDriverClassName("com.cj.mysql.jdbc.Driver");
        ds.setUrl("jdbc:mysql:///test");
        ds.setUsername("root");
        ds.setPassword("1234");
        return ds;
    }

}
