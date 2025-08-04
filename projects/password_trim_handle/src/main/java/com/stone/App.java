package com.stone;

import com.stone.config.SpringConfig;
import com.stone.service.ResourcesService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        ResourcesService resourcesService = context.getBean(ResourcesService.class);
        boolean flag = resourcesService.openURL("https://www.baidu.com", "root ");
        System.out.println(flag);
    }
}
