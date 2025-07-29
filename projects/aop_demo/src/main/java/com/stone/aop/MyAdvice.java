package com.stone.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component // 将该类交给Spring作为Bean管理
@Aspect // 声明该类是一个切面类
public class MyAdvice {

    // 定义通知
    @Around("pt()") // 定义切面，绑定切入点和通知的关系
    public Object calculateTime(ProceedingJoinPoint point) throws Throwable {
        // 记录程序执行开始时间
        Long startTime = System.currentTimeMillis();

        // 业务执行
        Object object = null;
        for (int i = 0; i < 10000; i++) {
            object = point.proceed();
        }

        // 记录程序执行结束时间
        Long endTime = System.currentTimeMillis();
        // 计算程序执行时间
        System.out.println("程序执行时间：" + (endTime - startTime) + "ms");

        return object;
    }

    // 定义切入点
    @Pointcut("execution(void com..*Dao.*te())")
    private void pt(){
    }
}
