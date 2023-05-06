package com.mall.cart.constant;

/**
 * @Author DongJunTao
 * @Description rabbit mq常量
 * @Date 2022/5/26 15:50
 * @Version 1.0
 */
public class RabbitMQConstant {

    //购物车延迟双删
    public static final String CART_DOUBLE_DELETE_DELAY_EXCHANGE = "cart_double_delete_delay_exchange";
    public static final String CART_DOUBLE_DELETE_DEAD_EXCHANGE = "cart_double_delete_dead_exchange"; //死信交换机
    public static final String CART_DOUBLE_DELETE_DELAY_QUEUE = "cart_double_delete_delay_queue";
    public static final String CART_DOUBLE_DELETE_DEAD_QUEUE = "cart_double_delete_dead_queue";//死信队列
    public static final String CART_DOUBLE_DELETE_DELAY_KEY = "cart_double_delete_delay_key";
    public static final String CART_DOUBLE_DELETE_DEAD_KEY = "cart_double_delete_dead_key";//死信队列key
    public static final Integer CART_DOUBLE_DELETE_DELAY_TIME = 1000; //延迟1000毫秒

}
