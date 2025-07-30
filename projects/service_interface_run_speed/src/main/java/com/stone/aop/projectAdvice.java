package com.stone.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class projectAdvice {

    @Around("servicePt()")
    public Object runSpeed(ProceedingJoinPoint point) throws Throwable {
        Long startTime = System.currentTimeMillis();
        Object result = null;

        for (int i = 0; i < 10000; i++) {
            result = point.proceed();
        }

        Long endTime = System.currentTimeMillis();
        // 获取原始执行方法的类/接口名称
        String className = point.getSignature().getDeclaringTypeName();
        // 获取原始执行方法的名称
        String methodName = point.getSignature().getName();
        System.out.println(className + " " + methodName + "方法执行时间：" + (endTime - startTime) + "ms");

        return result;
    }

    // 定义切入点：匹配业务层的所有方法
    @Pointcut("execution(* com..*Service.*(..))")
    private void servicePt() {}
}
