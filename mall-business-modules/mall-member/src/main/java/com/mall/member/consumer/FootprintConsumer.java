package com.mall.member.consumer;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mall.common.base.CommonResult;
import com.mall.common.base.constant.RabbitConstant;
import com.mall.log.api.dto.RabbitMQReceivedLogDTO;
import com.mall.log.api.feign.FeignRabbitMQReceivedLogService;
import com.mall.member.entity.FootprintEntity;
import com.mall.member.service.FootprintService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 接收商品服务推来的消息
 * @Date 2022/4/22 9:26
 * @Version 1.0
 */
@Component
@RabbitListener(queues = RabbitConstant.MEMBER_FOOTPRINT_QUEUE)
public class FootprintConsumer {

    @Autowired
    private FootprintService footprintService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FeignRabbitMQReceivedLogService feignRabbitMQReceivedLogService;

    /**
     * 监听是否有消息（是否需要记录足迹）
     * @param message
     */
    @RabbitHandler
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

            //先查询该用户是否已经浏览过该商品，如果否，创建记录，如果没有，修改时间
            FootprintEntity footprint = footprintService.getFootprintByParams(message);
            if (footprint == null) {
                footprint = new FootprintEntity();
                footprint.setMemberId(Long.valueOf(String.valueOf(message.get("userId"))));
                footprint.setGoodsId(Long.valueOf(String.valueOf(message.get("goodsId"))));
                footprint.setMerchantId(Long.valueOf(String.valueOf(message.get("merchantId"))));
                footprint.setCreateTime(new Date());
            } else {
                footprint.setUpdateTime(new Date());
            }
            //更新到数据库
            footprintService.saveOrUpdate(footprint);

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
