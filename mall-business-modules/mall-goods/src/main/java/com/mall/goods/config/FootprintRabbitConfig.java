package com.mall.goods.config;

import com.mall.common.base.constant.RabbitConstant;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 足迹记录 RabbitMQ
 * @Date 2022/5/26 15:42
 * @Version 1.0
 */
@Configuration
public class FootprintRabbitConfig {
    /**
     * 交换机
     */
    @Bean
    public DirectExchange exchange() {
        return ExchangeBuilder.directExchange(RabbitConstant.MEMBER_FOOTPRINT_EXCHANGE).durable(true).build();
    }
    /**
     * 队列
     * */
    @Bean
    public Queue queue(){
        return QueueBuilder.durable(RabbitConstant.MEMBER_FOOTPRINT_QUEUE).build();
    }
    /**
     * 声明队列与交换机绑定
     * */
    @Bean
    public Binding queueBindExchange(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(RabbitConstant.MEMBER_FOOTPRINT_KEY);
    }

}
