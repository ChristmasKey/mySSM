package com.stone;

import com.stone.config.SpringConfig;
import com.stone.controller.UserController;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        // 注册Spring配置类，效果等同写法：new AnnotationConfigApplicationContext(SpringConfig.class);
        context.register(SpringConfig.class);
        // 手动刷新上下文
        context.refresh();

        // 尝试获取Controller层的Bean，来验证excludeFilters过滤器是否生效
        // 注意：在运行main方法之前必须把SpringMvcConfig类中的@Configuration注解注释掉
        // 否则已经被排除的Controller层的Bean会被SpringMVC重新加载回来
        System.out.println(context.getBean(UserController.class));
    }
}
