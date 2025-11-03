package com.stone.controller;

import com.stone.exception.BusinessException;
import com.stone.exception.SystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // 将该类声明为异常处理器类
public class ProjectExceptionAdvice {

    @ExceptionHandler(BusinessException.class)
    public Result doBusinessException(BusinessException e) {
        // 记录日志
        // 发送消息给运维
        // 发送邮件给开发人员，ex对象发送给开发人员
        return new Result(null, e.getMessage(), e.getCode());
    }

    @ExceptionHandler(SystemException.class)
    public Result doSystemException(SystemException e) {
        return new Result(null, e.getMessage(), e.getCode());
    }


    @ExceptionHandler(Exception.class) // 定义要处理的异常类型
    public Result doException(Exception e) {
        // 记录日志
        // 发送消息给运维
        // 发送邮件给开发人员，ex对象发送给开发人员
        return new Result(null, "系统繁忙，请稍后再试！", Code.SYSTEM_UNKNOWN_ERR);
    }
}
