package com.mall.order.constant;

/**
 * @Author DongJunTao
 * @Description rabbit mq常量
 * @Date 2023/5/6 15:50
 * @Version 1.0
 */
public class RabbitMQConstant {

    //订单超时未支付自动取消
    public static final String ORDER_AUTO_CANCEL_DELAY_EXCHANGE = "order_auto_cancel_delay_exchange";
    public static final String ORDER_AUTO_CANCEL_DEAD_EXCHANGE = "order_auto_cancel_dead_exchange"; //死信交换机
    public static final String ORDER_AUTO_CANCEL_DELAY_QUEUE = "order_auto_cancel_delay_queue";
    public static final String ORDER_AUTO_CANCEL_DEAD_QUEUE = "order_auto_cancel_dead_queue";//死信队列
    public static final String ORDER_AUTO_CANCEL_DELAY_KEY = "order_auto_cancel_delay_key";
    public static final String ORDER_AUTO_CANCEL_DEAD_KEY = "order_auto_cancel_dead_key";//死信队列key
    public static final Integer ORDER_AUTO_CANCEL_DELAY_TIME = 1000*60*30; //延迟30分钟


}
