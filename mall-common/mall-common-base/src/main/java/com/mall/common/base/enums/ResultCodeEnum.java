package com.mall.common.base.enums;

/**
 * @Author DongJunTao
 * @Description 公共错误码
 * @Date 2021/4/26 20:25
 * @Version 1.0
 */
public enum ResultCodeEnum {
    SUCCESS("200","操作成功"),
    FAIL("500","系统异常"),
    UNAUTHORIZED("401", "未登录或token已过期"),
    FORBIDDEN("403","没有访问权限"),
    VALIDATE_FAILED("404", "参数检验失败"),

    USER_ACCOUNT_EXPIRED("450", "账号已过期"),
    USER_CREDENTIALS_ERROR("451", "用户名或密码错误"),
    USER_CREDENTIALS_EXPIRED("452", "密码已过期"),
    USER_ACCOUNT_DISABLE("453", "账号不可用"),
    USER_ACCOUNT_LOCKED("454", "账号被锁定"),
    USER_ACCOUNT_NOT_EXIST("455", "账号不存在"),
    ADMIN_COUNT_NOT_DELETED("456","管理员账号无法删除"),

    PLEASE_DELETE_CHILD_MENU_BUTTON("457","请先删除子菜单或按钮"),
    MENU_NAME_NOT_BE_EMPTY("458", "菜单名称不能为空"),
    PARENT_MENU_NOT_BE_EMPTY("459", "上级菜单不能为空"),
    MENU_URL_NOT_BE_EMPTY("460","菜单URL不能为空"),
    PARENT_MENU_IS_ONLY_CATALOG("461","上级菜单只能为目录类型"),
    PARENT_MENU_IS_ONLY_MENU("462","上级菜单只能为菜单类型"),


    COMMON_FAIL("10000", "其他错误");

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