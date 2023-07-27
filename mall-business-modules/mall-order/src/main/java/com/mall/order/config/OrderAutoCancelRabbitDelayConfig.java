package com.mall.order.config;

import com.mall.common.base.constant.RabbitConstant;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 订单超时未支付自动取消 RabbitMQ
 * @Date 2023/5/6 15:42
 * @Version 1.0
 */
@Configuration
public class OrderAutoCancelRabbitDelayConfig {

    /**
     * 延时交换机
     * @return
     */
    @Bean
    public DirectExchange delayExchange() {
       return ExchangeBuilder.directExchange(RabbitConstant.ORDER_AUTO_CANCEL_DELAY_EXCHANGE).durable(true).build();
    }
    /**
     * 延时队列
     * @return
     */
    @Bean
    public Queue delayQueue() {
        Map<String, Object> map = new HashMap<>();
        // 设置30分钟过期时间
        map.put("x-message-ttl", RabbitConstant.ORDER_AUTO_CANCEL_DELAY_TIME);
        map.put("x-dead-letter-exchange", RabbitConstant.ORDER_AUTO_CANCEL_DEAD_EXCHANGE);
        map.put("x-dead-letter-routing-key", RabbitConstant.ORDER_AUTO_CANCEL_DEAD_KEY);
        return QueueBuilder.durable(RabbitConstant.ORDER_AUTO_CANCEL_DELAY_QUEUE).withArguments(map).build();
    }
    /**
     * 将延迟队列通过routingKey绑定到延迟交换器
     * @return
     */
    @Bean
    public Binding delayQueueBindExchange(DirectExchange delayExchange, Queue delayQueue) {
        return BindingBuilder.bind(delayQueue).to(delayExchange).with(RabbitConstant.ORDER_AUTO_CANCEL_DELAY_KEY);
    }


    /**
     * 死信交换机
     */
    @Bean
    public DirectExchange deadExchange() {
        return ExchangeBuilder.directExchange(RabbitConstant.ORDER_AUTO_CANCEL_DEAD_EXCHANGE).durable(true).build();
    }
    /**
     * 死信队列
     * */
    @Bean
    public Queue deadQueue(){
        return QueueBuilder.durable(RabbitConstant.ORDER_AUTO_CANCEL_DEAD_QUEUE).build();
    }
    /**
     * 声明死信队列与死信交换机绑定
     * */
    @Bean
    public Binding deadQueueBindExchange(Queue deadQueue, DirectExchange deadExchange) {
        return BindingBuilder.bind(deadQueue).to(deadExchange).with(RabbitConstant.ORDER_AUTO_CANCEL_DEAD_KEY);
    }

}
