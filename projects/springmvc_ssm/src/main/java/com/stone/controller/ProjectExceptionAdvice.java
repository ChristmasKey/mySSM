package com.stone.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // 将该类声明为异常处理器类
public class ProjectExceptionAdvice {

    @ExceptionHandler(Exception.class) // 定义要处理的异常类型
    public Result doException(Exception e) {
        System.out.println("捕获到异常：" + e.getMessage());
        return new Result(null, e.getMessage(), 500);
    }
}
