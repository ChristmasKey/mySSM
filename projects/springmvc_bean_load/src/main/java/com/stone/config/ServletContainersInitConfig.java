package com.stone.config;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;

// 简化写法
public class ServletContainersInitConfig extends AbstractAnnotationConfigDispatcherServletInitializer {
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{SpringConfig.class};
    }

    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{SpringMvcConfig.class};
    }

    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}

// 标准写法
// public class ServletContainersInitConfig extends AbstractDispatcherServletInitializer {
//
//     protected WebApplicationContext createServletApplicationContext() {
//         AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
//         context.register(SpringMvcConfig.class);
//         return context;
//     }
//
//     protected String[] getServletMappings() {
//         return new String[]{"/"};
//     }
//
//     // 加载Spring配置
//     protected WebApplicationContext createRootApplicationContext() {
//         // 加载Spring配置的写法与加载SpringMVC配置的写法是一样的
//         AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
//         // 唯一的区别就是修改对应的配置类
//         context.register(SpringMvcConfig.class);
//         return context;
//     }
// }
