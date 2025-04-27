package com.vincent.exception;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.vincent.common.Result;
import com.vincent.enums.ResultCodeEnum;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理器
 */
@ControllerAdvice // 可以加 basePackages("com.vincent.controller") 也可以省略，让它全局生效
public class GlobalExceptionHandler {

    private static final Log log = LogFactory.get();

    /**
     * 捕获系统异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(HttpServletRequest request, Exception e) {
        log.error("系统异常：", e);
        return Result.error(ResultCodeEnum.SYSTEM_ERROR); // ✅ 更规范
    }


    /**
     * 捕获自定义业务异常
     */
    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public Result customError(HttpServletRequest request, CustomException e) {
        return Result.error(e.getCode(), e.getMessage()); // getMessage() 是 RuntimeException 中自带的方法
    }
}
