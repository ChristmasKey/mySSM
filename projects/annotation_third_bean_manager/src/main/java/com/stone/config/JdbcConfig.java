package com.stone.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.stone.dao.BookDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

public class JdbcConfig {

    // 通过成员变量注入简单类型
    @Value("com.cj.mysql.jdbc.Driver")
    private String driver;
    @Value("jdbc:mysql:///test")
    private String url;
    @Value("root")
    private String username;
    @Value("1234")
    private String password;

    // 1.定义一个方法获得要管理的对象
    // 2.通过@Bean注解将方法的返回值注册成Bean对象
    @Bean
    public DataSource dataSource(BookDao bookDao) {
        // 通过自动装配注入引用类型
        System.out.println(bookDao);
        DruidDataSource ds = new DruidDataSource();
        ds.setDriverClassName(driver);
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        return ds;
    }

}
