package com.mall.common.base;

import com.mall.common.base.enums.ResultCodeEnum;

/**
 * @Author DongJunTao
 * @Description 公共返回对象
 * @Date 2021/4/27 18:54
 * @Version 1.0
 */
public class CommonResult<T> {
    /**
     * 状态码
     */
    private String code;
    /**
     * 提示信息
     */
    private String message;
    /**
     * 数据封装
     */
    private T data;

    public CommonResult() {}

    protected CommonResult(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    protected CommonResult(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 成功返回结果
     */
    public static <T> CommonResult<T> success() {
        return new CommonResult<T>(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getMessage());
    }
    /**
     * 成功返回结果
     * @param data 获取的数据
     */
    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<T>(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getMessage(), data);
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     * @param  code 返回码
     */
    public static <T> CommonResult<T> success(String code, String message, T data) {
        return new CommonResult<T>(code, message, data);
    }

    /**
     * 成功返回结果
     * @param  code 返回码
     */
    public static <T> CommonResult<T> success(String code, String message) {
        return new CommonResult<T>(code, message);
    }


    /**
     * 失败返回结果
     */
    public static <T> CommonResult<T> fail() {
        return new CommonResult<T>(ResultCodeEnum.FAIL.getCode(), ResultCodeEnum.FAIL.getMessage());
    }

    /**
     * 失败返回结果
     */
    public static <T> CommonResult<T> fail(String code, String message) {
        return new CommonResult<T>(code, message);
    }

    /**
     * 失败返回结果
     */
    public static <T> CommonResult<T> fail(String code, String message, T data) {
        return new CommonResult<T>(code, message, data);
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
