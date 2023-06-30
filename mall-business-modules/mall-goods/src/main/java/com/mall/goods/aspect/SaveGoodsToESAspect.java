package com.mall.goods.aspect;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mall.common.base.constant.RabbitConstant;
import com.mall.log.api.dto.RabbitMQSendLogDTO;
import com.mall.log.api.feign.FeignRabbitMQSendLogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author DongJunTao
 * @Description 商品同步到ES
 * @Date 2023/6/20 14:13
 * @Version 1.0
 */
@Component
@Aspect
@Slf4j
public class SaveGoodsToESAspect {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FeignRabbitMQSendLogService feignRabbitMQSendLogService;

    /**
     * 商品申请时，审核通过，商品保存到ES
     */
    @Pointcut("execution(* com.mall.goods.service.GoodsService.audit(..))")
    private void saveGoodsToESAfterAudit() {}

    /**
     * 拦截service,发送消息通知
     * @param returnVal
     */
    @AfterReturning(value = "saveGoodsToESAfterAudit()", returning = "returnVal")
    private void saveGoodsToESAfterAudit(JoinPoint point, int returnVal) throws JsonProcessingException {
        Object[] args = point.getArgs();
        Long goodsId = Long.parseLong(args[0].toString());
        Boolean isAudit = Boolean.parseBoolean(args[1].toString());
        //审核通过，且数据库更新成功
        if (isAudit && returnVal > 0) {
            Map<String, Object> message = new HashMap<>();
            message.put("goodsId",goodsId);
            //构建相关数据
            String uuid = UUID.randomUUID().toString();
            CorrelationData correlationData = new CorrelationData(uuid);//消息唯一id,用于区分消息
            Message returnMessage = MessageBuilder.withBody(objectMapper.writeValueAsBytes(message))
                    .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                    .setDeliveryMode(MessageDeliveryMode.PERSISTENT)
                    .setReceivedExchange(RabbitConstant.SAVE_GOODS_TO_ES_EXCHANGE)
                    .setReceivedRoutingKey(RabbitConstant.SAVE_GOODS_TO_ES_KEY)
                    .build();
            correlationData.setReturnedMessage(returnMessage);

            //先记录到日志，用于后续发消息失败，有兜底方案
            RabbitMQSendLogDTO mqSendLogDTO = new RabbitMQSendLogDTO();
            mqSendLogDTO.setMessageId(uuid);
            mqSendLogDTO.setExchange(RabbitConstant.SAVE_GOODS_TO_ES_EXCHANGE);
            mqSendLogDTO.setRouteKey(RabbitConstant.SAVE_GOODS_TO_ES_KEY);
            mqSendLogDTO.setSendContent(JSONUtil.toJsonStr(message));
            feignRabbitMQSendLogService.save(mqSendLogDTO);

            // 指定之前定义的延迟交换机名 与路由键名
            rabbitTemplate.convertAndSend(RabbitConstant.SAVE_GOODS_TO_ES_EXCHANGE,
                    RabbitConstant.SAVE_GOODS_TO_ES_KEY, message, correlationData);
        }
    }
}
