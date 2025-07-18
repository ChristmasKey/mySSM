package com.stone;

import com.stone.config.SpringConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;

public class App {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        DataSource dataSource = context.getBean(DataSource.class);
        System.out.println(dataSource);
    }
}
