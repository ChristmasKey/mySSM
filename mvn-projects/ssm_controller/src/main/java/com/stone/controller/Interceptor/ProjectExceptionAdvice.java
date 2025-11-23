package com.stone.controller.Interceptor;

import com.stone.controller.results.Result;
import com.stone.system.exception.BusinessException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Component
@ControllerAdvice
public class ProjectExceptionAdvice {

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public Result doBusinessException(BusinessException ex) {
        return new Result(ex.getCode(), ex.getMessage());
    }
}
