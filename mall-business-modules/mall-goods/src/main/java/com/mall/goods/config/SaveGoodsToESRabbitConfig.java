package com.mall.goods.config;

import com.mall.common.base.constant.RabbitConstant;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 商品上架审核通过后，保存商品到ES RabbitMQ
 * @Date 2023/6/20 19:42
 * @Version 1.0
 */
@Configuration
public class SaveGoodsToESRabbitConfig {
    /**
     * 交换机
     */
    @Bean
    public DirectExchange exchange() {
        return ExchangeBuilder.directExchange(RabbitConstant.SAVE_GOODS_TO_ES_EXCHANGE).durable(true).build();
    }
    /**
     * 队列
     * */
    @Bean
    public Queue queue(){
        return QueueBuilder.durable(RabbitConstant.SAVE_GOODS_TO_ES_QUEUE).build();
    }
    /**
     * 声明队列与交换机绑定
     * */
    @Bean
    public Binding queueBindExchange(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(RabbitConstant.SAVE_GOODS_TO_ES_KEY);
    }

}
