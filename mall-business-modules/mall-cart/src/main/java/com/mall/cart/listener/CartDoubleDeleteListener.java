package com.mall.cart.listener;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mall.cart.constant.RedisKeyConstant;
import com.mall.common.base.CommonResult;
import com.mall.common.base.constant.RabbitConstant;
import com.mall.common.seata.util.RedisUtil;
import com.mall.log.api.dto.RabbitMQReceivedLogDTO;
import com.mall.log.api.feign.FeignRabbitMQReceivedLogService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 购物车 redis 双删
 * @Date 2022/5/26 19:26
 * @Version 1.0
 */
@Component
public class CartDoubleDeleteListener {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FeignRabbitMQReceivedLogService feignRabbitMQReceivedLogService;

    /**
     * 延时双删
     * @param message
     */
    @RabbitListener(queues = RabbitConstant.CART_DOUBLE_DELETE_DEAD_QUEUE)
    public void process(Map<String, Object> message, Channel channel, Message mqMessage) {
        RabbitMQReceivedLogDTO rabbitMQReceivedLogDTO = new RabbitMQReceivedLogDTO();
        try {
            //保证待消费的消息
            String exchange = mqMessage.getMessageProperties().getReceivedExchange();
            String routingKey = mqMessage.getMessageProperties().getReceivedRoutingKey();
            String consumerQueue  = mqMessage.getMessageProperties().getConsumerQueue();
            String correlationId  = String.valueOf(mqMessage.getMessageProperties().getHeaders()
                    .get("spring_returned_message_correlation"));
            Map<String,Object> returnMessage = objectMapper.readValue(mqMessage.getBody(), Map.class);
            rabbitMQReceivedLogDTO.setExchange(exchange);
            rabbitMQReceivedLogDTO.setMessageId(correlationId);
            rabbitMQReceivedLogDTO.setRouteKey(routingKey);
            rabbitMQReceivedLogDTO.setQueueName(consumerQueue);
            rabbitMQReceivedLogDTO.setReceivedContent(JSONUtil.toJsonStr(returnMessage));
            feignRabbitMQReceivedLogService.save(rabbitMQReceivedLogDTO);

            //延迟二次删除
            RedisUtil.hDel(RedisKeyConstant.CART_KEY, String.valueOf(message.get("memberId")));

            //确认
            channel.basicAck(mqMessage.getMessageProperties().getDeliveryTag(), false);

            //消费成功，修改数据库状态
            rabbitMQReceivedLogDTO.setStatus(1);
            feignRabbitMQReceivedLogService.update(rabbitMQReceivedLogDTO);
        } catch (Exception e) {
            try {
                //开始重试，达到既定次数，终止重试
                rabbitMQReceivedLogDTO.setStatus(2); //消费失败重试
                CommonResult result = feignRabbitMQReceivedLogService.update(rabbitMQReceivedLogDTO);
                if (result != null && "200".equals(result.getCode())) { //重试消费
                    channel.basicReject(mqMessage.getMessageProperties().getDeliveryTag(), true);
                } else {
                    channel.basicReject(mqMessage.getMessageProperties().getDeliveryTag(), false);
                }
            } catch (Exception e2) {}
        }
    }

}
