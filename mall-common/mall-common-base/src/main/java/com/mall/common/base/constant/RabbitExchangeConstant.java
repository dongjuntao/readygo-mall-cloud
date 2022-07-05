package com.mall.common.base.constant;

/**
 * @Author DongJunTao
 * @Description Rabbit MQ exchange常量
 * @Date 2022/4/21 19:06
 * @Version 1.0
 */
public class RabbitExchangeConstant {

    public static final String FOOTPRINT_EXCHANGE = "footprint_exchange"; //会员足迹记录

    public static final String CART_DOUBLE_DELETE_EXCHANGE = "cart_double_delete_exchange"; //redis双删【购物车】

    public static final String ALIPAY_SUCCESS_EXCHANGE = "alipay_success_exchange"; //支付宝支付成功发送通知

}
