package com.mall.common.rabbitmq.config;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mall.common.base.CommonResult;
import com.mall.common.base.constant.RabbitConstant;
import com.mall.log.api.dto.RabbitMQSendLogDTO;
import com.mall.log.api.feign.FeignRabbitMQSendLogService;
import lombok.SneakyThrows;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.connection.PublisherCallbackChannel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description rabbit confirm and return
 * @Date 2023/5/11 10:25
 * @Version 1.0
 */
@Component
public class RabbitCallBack implements RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnCallback {

    private static Logger logger = LoggerFactory.getLogger(RabbitCallBack.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private FeignRabbitMQSendLogService feignRabbitMQSendLogService;

    @PostConstruct
    public void init() {
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback(this::confirm);
        rabbitTemplate.setReturnCallback(this::returnedMessage);
    }

    /**
     *
     * @param correlationData 消息属性体
     * @param ack 是否成功，成功到达true，没有到达，false
     * @param cause rabbitmq自身给的信息
     */
    @SneakyThrows
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (correlationData == null) {
            return;
        }
        String id = correlationData.getId();
        Message message = correlationData.getReturnedMessage();
        //消息发送失败，重新发送，最多五次，发送失败的话，入数据库，定时消费
        RabbitMQSendLogDTO mqSendLogDTO = new RabbitMQSendLogDTO();
        mqSendLogDTO.setMessageId(id);
        if (!ack) { //消息发送失败处理
            mqSendLogDTO.setStatus(2);
            mqSendLogDTO.setCause(cause);
            CommonResult result = feignRabbitMQSendLogService.update(mqSendLogDTO);//更新数据库信息
            if (result != null && "200".equals(result.getCode())) { //重新发送
                String exchange = String.valueOf(message.getMessageProperties().getHeaders()
                        .get("receivedExchange"));
                String routingKey = String.valueOf(message.getMessageProperties().getHeaders()
                        .get("receivedRoutingKey"));
                Map<String,Object> returnMessage = objectMapper.readValue(message.getBody(), Map.class);
                CorrelationData data = buildCorrelationData(id, message,exchange,routingKey);
                rabbitTemplate.convertAndSend(exchange, routingKey, returnMessage, data);
            }
            //重新发送消息
            logger.info("交换机未成功收到id 为:{}消息，原因:{}", id, cause);
        } else { //消息发送成功处理
            mqSendLogDTO.setStatus(1);
            mqSendLogDTO.setCause(null);
            feignRabbitMQSendLogService.update(mqSendLogDTO);//更新数据库信息
            logger.info("交换机成功收到id 为:{}消息！", id);
        }
    }

    @SneakyThrows
    @Override
    public void returnedMessage(Message message, int i, String cause, String exchange, String routingKey) {
        if (message == null) {
            return;
        }
        String id = String.valueOf(message.getMessageProperties().getHeaders()
                .get(PublisherCallbackChannel.RETURNED_MESSAGE_CORRELATION_KEY));
        if (Strings.isEmpty(id)) {
            return;
        }
        RabbitMQSendLogDTO mqSendLogDTO = new RabbitMQSendLogDTO();
        mqSendLogDTO.setMessageId(id);
        mqSendLogDTO.setStatus(1);
        mqSendLogDTO.setCause(cause);
        mqSendLogDTO.setExchange(exchange);
        mqSendLogDTO.setRouteKey(routingKey);
        CommonResult result = feignRabbitMQSendLogService.update(mqSendLogDTO);
        if (result != null && "200".equals(result.getCode())) { //重新发送
            Map<String,Object> returnMessage = objectMapper.readValue(message.getBody(), Map.class);
            CorrelationData data = buildCorrelationData(id, message, exchange, routingKey);
            rabbitTemplate.convertAndSend(exchange, routingKey, returnMessage, data);
        }
        logger.info("交换机未成功收到id 为:{}消息，原因:{}", id, cause);
    }

    /**
     * 构建 关联数据 CorrelationData
     * @param uuid
     * @param message
     * @param exchange
     * @param routingKey
     * @return
     * @throws JsonProcessingException
     */
    private CorrelationData buildCorrelationData(String uuid,
                                                 Message message,
                                                 String exchange,
                                                 String routingKey) throws JsonProcessingException {
        CorrelationData correlationData = new CorrelationData(uuid);//消息唯一id,用于区分消息
        Message returnMessage = MessageBuilder.withBody(objectMapper.writeValueAsBytes(message))
                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .setDeliveryMode(MessageDeliveryMode.PERSISTENT)
                .setReceivedExchange(exchange)
                .setReceivedRoutingKey(routingKey)
                .build();
        correlationData.setReturnedMessage(returnMessage);
        return correlationData;
    }
}
