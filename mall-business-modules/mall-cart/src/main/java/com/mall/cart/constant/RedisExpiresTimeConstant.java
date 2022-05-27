package com.mall.cart.constant;

/**
 * @Author DongJunTao
 * @Description redis过期时间常量
 * @Date 2022/5/25 17:04
 * @Version 1.0
 */
public class RedisExpiresTimeConstant {

    /** 购物车信息 过期时间是 一周（7*24=604800秒）*/
    public static final Long CART_EXPIRED_TIME = 604800L;
}
