package com.mall.common.base.constant;

/**
 * @Author DongJunTao
 * @Description rabbit mq相关常量
 * @Date 2023/5/8 15:32
 * @Version 1.0
 */
public class RabbitConstant {

    //购物车延迟双删
    public static final String CART_DOUBLE_DELETE_DELAY_EXCHANGE = "cart_double_delete_delay_exchange";
    public static final String CART_DOUBLE_DELETE_DEAD_EXCHANGE = "cart_double_delete_dead_exchange"; //死信交换机
    public static final String CART_DOUBLE_DELETE_DELAY_QUEUE = "cart_double_delete_delay_queue";
    public static final String CART_DOUBLE_DELETE_DEAD_QUEUE = "cart_double_delete_dead_queue";//死信队列
    public static final String CART_DOUBLE_DELETE_DELAY_KEY = "cart_double_delete_delay_key";
    public static final String CART_DOUBLE_DELETE_DEAD_KEY = "cart_double_delete_dead_key";//死信队列key
    public static final Integer CART_DOUBLE_DELETE_DELAY_TIME = 1000; //延迟1000毫秒

    //订单超时未支付自动取消
    public static final String ORDER_AUTO_CANCEL_DELAY_EXCHANGE = "order_auto_cancel_delay_exchange";
    public static final String ORDER_AUTO_CANCEL_DEAD_EXCHANGE = "order_auto_cancel_dead_exchange"; //死信交换机
    public static final String ORDER_AUTO_CANCEL_DELAY_QUEUE = "order_auto_cancel_delay_queue";
    public static final String ORDER_AUTO_CANCEL_DEAD_QUEUE = "order_auto_cancel_dead_queue";//死信队列
    public static final String ORDER_AUTO_CANCEL_DELAY_KEY = "order_auto_cancel_delay_key";
    public static final String ORDER_AUTO_CANCEL_DEAD_KEY = "order_auto_cancel_dead_key";//死信队列key
    public static final Integer ORDER_AUTO_CANCEL_DELAY_TIME = 1000*60*30; //延迟30分钟


    //商品审核通后上架，保存
    public static final String SAVE_GOODS_TO_ES_EXCHANGE = "save_goods_to_es_exchange";
    public static final String SAVE_GOODS_TO_ES_QUEUE = "save_goods_to_es_queue";
    public static final String SAVE_GOODS_TO_ES_KEY = "save_goods_to_es_key";

    //商品下架后，保存
    public static final String DELETE_GOODS_FROM_ES_EXCHANGE = "delete_goods_from_es_exchange";
    public static final String DELETE_GOODS_FROM_ES_QUEUE = "delete_goods_from_es_queue";
    public static final String DELETE_GOODS_FROM_ES_KEY = "delete_goods_from_es_key";

}
