package com.stone.config;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;

// 定义一个Servlet容器启动的配置类，在其中加载Spring的配置
public class ServletContainersInitConfig extends AbstractDispatcherServletInitializer {
    // 加载SpringMVC容器配置类
    @Override
    protected WebApplicationContext createServletApplicationContext() {
        // 这个对象类似于前面学习Spring时的 AnnotationConfigApplicationContext对象
        // 区别在于它是专门用于Web环境下加载 SpringMVC 配置类的
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.register(SpringMvcConfig.class);
        return ctx;
    }

    // 设置SpringMVC的请求映射路径
    // 指定哪些请求归属SpringMVC处理
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    // 加载Spring容器配置类
    @Override
    protected WebApplicationContext createRootApplicationContext() {
        return null;
    }
}
