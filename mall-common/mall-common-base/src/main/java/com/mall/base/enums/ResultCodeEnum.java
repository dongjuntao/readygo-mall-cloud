package com.mall.base.enums;

/**
 * @Author DongJunTao
 * @Description 公共错误码
 * @Date 2021/4/26 20:25
 * @Version 1.0
 */
public enum ResultCodeEnum {
    SUCCESS("200","操作成功"),
    FAIL("500","系统异常"),
    VALIDATE_FAILED("404", "参数检验失败"),
    FORBIDDEN("403","没有访问权限"),
    UNAUTHORIZED("401", "未登录或token已过期");

    private String code;
    private String message;

    ResultCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
