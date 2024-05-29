package com.lanf.system.handler;

import com.lanf.common.exception.CacheExpiredException;
import com.lanf.common.result.Result;
import com.lanf.system.exception.LanfException;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 全局异常处理类
 */
@ControllerAdvice
@Order(2)
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e) {
        e.printStackTrace();
        return Result.fail();
    }

    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public Result error(ArithmeticException e) {
        e.printStackTrace();
        return Result.fail().message("除數不能為0");
    }

    @ExceptionHandler(LanfException.class)
    @ResponseBody
    public Result error(LanfException e) {
        e.printStackTrace();
        return Result.fail().message(e.getMessage()).code(e.getCode());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public Result error(RuntimeException e) {
        e.printStackTrace();
        return Result.fail().message(e.getMessage());
    }

    @ExceptionHandler(CacheExpiredException.class)
    @ResponseBody
    public Result error(CacheExpiredException e) {
        e.printStackTrace();
        return Result.fail().message(e.getMessage()).code(e.getCode());
    }


}
