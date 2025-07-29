package com.stone.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class MyAdvice {

    // @Before("pt()")
    public void before() {
        System.out.println("前置通知");
    }

    // @After("selectPt()")
    public void after() {
        System.out.println("后置通知");
    }

    // @AfterReturning("selectPt()")
    public void afterReturning() {
        System.out.println("返回通知");
    }

    @AfterThrowing("selectPt()")
    public void afterThrowing() {
        System.out.println("异常通知");
    }

    // 环绕通知的标准写法应该如下
    @Around("pt()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("环绕通知1");
        // 表示对原始操作的调用
        Object res = pjp.proceed();
        System.out.println("环绕通知2");

        System.out.println("查看void方法的返回值 " + res);

        return res;
    }

    @Pointcut("execution(void com.stone.dao.BookDao.update())")
    public void pt() {}

    // @Around("selectPt()")
    public Object selectAround(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("环绕通知1");
        // 表示对原始操作的调用
        Integer res = (Integer) pjp.proceed();
        System.out.println("环绕通知2");
        // 返回原始操作的结果（也可以对返回结果做一些操作）
        return res + 200;
    }

    @Pointcut("execution(int com.stone.dao.BookDao.select())")
    public void selectPt() {
    }
}
