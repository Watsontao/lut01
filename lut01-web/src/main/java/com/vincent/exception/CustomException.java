package com.vincent.exception;

import com.vincent.enums.ResultCodeEnum;

/**
 * 自定义业务异常类（用于全局统一处理）
 */
public class CustomException extends RuntimeException {

    private final String code; // ✅ 改为 String

    public CustomException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMsg());
        this.code = resultCodeEnum.getCode();
    }

    public String getCode() {
        return code;
    }
}
