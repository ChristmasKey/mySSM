package com.stone.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class DataAdvice {

    @Pointcut("execution(* com.stone.service.ResourcesService.openURL(..))")
    private void servicePt() {
    }

    @Around("servicePt()")
    public Object trimStr(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        if (args != null && args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                // 如果参数是String类型，则做trim操作
                if (args[i] instanceof String) {
                    args[i] = ((String) args[i]).trim();
                }
            }
        }
        return pjp.proceed(args);
    }
}
