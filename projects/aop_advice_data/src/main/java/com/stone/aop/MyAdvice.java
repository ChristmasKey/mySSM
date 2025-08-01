package com.stone.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
public class MyAdvice {

    @Pointcut("execution(* com.stone.dao.BookDao.findName(..))")
    private void pt() {}

    @Before("pt()")
    public void before(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        System.out.println("前置通知，参数列表：" + Arrays.toString(args));
    }

    @After("pt()")
    public void after(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        System.out.println("后置通知，参数列表：" + Arrays.toString(args));
    }

    // @AfterReturning("pt()")
    public void afterReturning() {
        System.out.println("返回通知");
    }

    // @AfterThrowing("pt()")
    public void afterThrowing() {
        System.out.println("异常通知");
    }

    // @Around("pt()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("环绕通知：方法执行前");
        Object res = pjp.proceed();
        System.out.println("环绕通知：方法执行后");
        return res;
    }
}
