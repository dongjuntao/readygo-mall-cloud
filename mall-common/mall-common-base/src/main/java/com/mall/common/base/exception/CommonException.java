package com.mall.common.base.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author DongJunTao
 * @Description 公共异常类
 * @Date 2021/6/10 10:28
 * @Version 1.0
 */
@Data
@NoArgsConstructor
public class CommonException extends RuntimeException{

    private String message;

    private String code;

    public CommonException(String message) {
        super(message);
        this.message = message;
    }

    public CommonException(String message, Throwable throwable){
        super(message, throwable);
        this.message = message;
    }

    public CommonException(String message, String code) {
        super(message);
        this.message = message;
        this.code = code;
    }

    public CommonException(String message, String code, Throwable throwable) {
        super(message, throwable);
        this.message = message;
        this.code = code;
    }
}
