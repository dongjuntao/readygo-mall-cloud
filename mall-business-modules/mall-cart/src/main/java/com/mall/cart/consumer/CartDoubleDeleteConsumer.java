package com.mall.cart.consumer;

import com.mall.cart.constant.RabbitMQConstant;
import com.mall.cart.constant.RedisKeyConstant;
import com.mall.common.redis.util.RedisUtil;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 购物车 redis 双删
 * @Date 2022/5/26 19:26
 * @Version 1.0
 */
@Component
public class CartDoubleDeleteConsumer {

    /**
     * 延时双删
     * @param message
     */
    @RabbitListener(queues = RabbitMQConstant.CART_DOUBLE_DELETE_DEAD_QUEUE)
    public void process(Map<String, Object> message) {
        //延迟二次删除
        RedisUtil.hDel(RedisKeyConstant.CART_KEY, String.valueOf(message.get("memberId")));
    }

}
