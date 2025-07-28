package com.stone.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component // 将该类交给Spring作为Bean管理
@Aspect // 声明该类是一个切面类
public class MyAdvice {

    // 定义通知
    @Before("pointcut()") // 定义切面，绑定切入点和通知的关系
    public void calculateTime(){
        System.out.println("计算时间");
    }

    // 定义切入点
    @Pointcut("execution(void com.stone.dao.BookDao.update())")
    private void pointcut(){
    }
}
