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
    private void pt() {
    }

    //@Before("pt()")
    public void before(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        System.out.println("前置通知，参数列表：" + Arrays.toString(args));
    }

    //@After("pt()")
    public void after(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        System.out.println("后置通知，参数列表：" + Arrays.toString(args));
    }

    //@AfterReturning(value = "pt()", returning = "res") // 通过注解中的returning属性指明用来接收返回值的形参，属性值和形参名必须保持一致
    public void afterReturning(JoinPoint joinPoint, Object res) { // 形参中的顺序要求：JoinPoint（如果有）必须在第一个，返回值在后
        Object[] args = joinPoint.getArgs();
        System.out.println("返回通知，参数列表：" + Arrays.toString(args));
        System.out.println("返回通知，返回值：" + res);
    }

    @AfterThrowing(value = "pt()", throwing = "e") // 通过注解中的throwing属性指明用来接收异常的形参，属性值和形参名必须保持一致
    public void afterThrowing(JoinPoint joinPoint, Throwable e) { // 形参中的顺序要求：JoinPoint（如果有）必须在第一个，异常在后
        Object[] args = joinPoint.getArgs();
        System.out.println("异常通知，参数列表：" + Arrays.toString(args));
        System.out.println("异常通知，异常信息：" + e.getMessage());
    }

    //@Around("pt()")
    public Object around(ProceedingJoinPoint pjp) {
        //ProceedingJoinPoint是JoinPoint的子接口
        Object[] args = pjp.getArgs();
        System.out.println("环绕通知，参数列表：" + Arrays.toString(args));
        //Object res = pjp.proceed();
        //我们可以对获取的参数进行校验和加工后，再传给原始操作去执行
        args[0] = 666;
        Object res = null;
        try {
            res = pjp.proceed(args);
        } catch (Throwable e) {
            System.out.println("环绕通知：方法执行异常" + e.getMessage());
        }
        System.out.println("环绕通知：方法执行后");
        return res;
    }
}
