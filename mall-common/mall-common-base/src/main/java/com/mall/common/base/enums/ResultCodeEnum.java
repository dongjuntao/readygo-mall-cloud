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
    CREATE_FAIL("420","新增失败"),
    UPDATE_FAIL("421","修改失败"),
    ENABLE_FAIL("422", "启用或禁用失败"),

    USER_ACCOUNT_EXPIRED("450", "账号已过期"),
    USER_CREDENTIALS_ERROR("451", "用户名或密码错误"),
    USER_CREDENTIALS_EXPIRED("452", "密码已过期"),
    USER_ACCOUNT_DISABLE("453", "账号不可用"),
    USER_ACCOUNT_LOCKED("454", "账号被锁定"),
    USER_ACCOUNT_NOT_EXIST("455", "账号不存在"),
    ADMIN_COUNT_NOT_DELETED("456","管理员账号无法删除"),
    USER_NAME_EXIST("457","用户名已存在"),
    SYSTEM_ROLE_NOT_DELETED("458","系统内置角色无法删除"),
    OLD_PASSWORD_ERROR("459","旧密码错误"),

    PLEASE_DELETE_CHILD_MENU_BUTTON("510","请先删除子菜单或按钮"),
    MENU_NAME_NOT_BE_EMPTY("511", "菜单名称不能为空"),
    PARENT_MENU_NOT_BE_EMPTY("512", "上级菜单不能为空"),
    MENU_URL_NOT_BE_EMPTY("513","菜单URL不能为空"),
    PARENT_MENU_IS_ONLY_CATALOG("514","上级菜单只能为目录类型"),
    PARENT_MENU_IS_ONLY_MENU("515","上级菜单只能为菜单类型"),

    LOGISTICS_COMPANY_EXIST("600","物流公司已存在"),
    HOMEPAGE_CAROUSEL_EXIST("601","首页轮播图已存在"),
    HOMEPAGE_NAVBAR_EXIST("602","首页导航已存在"),
    HOMEPAGE_PLATE_EXIST("603","首页板块已存在"),

    SECKILL_GOODS_IS_EXIST_IN_PERIOD("604","该时间段已设置该商品为秒杀商品，不可重复设置"),

    CART_IS_EXIST("700", "该商品已添加到购物车，无需重复添加"),
    COLLECT_GOODS_IS_EXIST("701", "该商品已收藏，无需重复收藏"),
    COUPON_INVALID("702", "优惠券不存在或已失效"),
    COUPON_RECEIVE_TOO_MANY_TIMES("703", "已超出限领次数"),
    COUPON_IS_NOT_EXPIRED("704", "优惠券已过期"),
    COUPON_IS_EMPTY("705", "优惠券已被领完"),
    COUPON_ISSUE_NUMBER_VALID("706", "发行量不能低于现有的发行量"),

    GOODS_NOT_EXIST("800", "商品不存在"),


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
