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
 * @Description
 * @Date 2023/6/21 21:49
 * @Version 1.0
 */
@Component
@Aspect
@Slf4j
public class DeleteGoodsFromESAspect {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FeignRabbitMQSendLogService feignRabbitMQSendLogService;

    /**
     * 商品下架时，需要通知ES，删除商品信息
     */
    @Pointcut("execution(* com.mall.goods.service.GoodsService.offShelf(..))")
    private void deleteGoodsFromESWhenOffShelf() {}

    /**
     * 拦截service,发送消息通知
     * @param returnVal
     */
    @AfterReturning(value = "deleteGoodsFromESWhenOffShelf()", returning = "returnVal")
    private void deleteGoodsFromESWhenOffShelf(JoinPoint point, int returnVal) throws JsonProcessingException {
        Object[] args = point.getArgs();
        Long goodsId = Long.parseLong(args[0].toString());
        //下架成功
        if (returnVal > 0) {
            Map<String, Object> message = new HashMap<>();
            message.put("goodsId",goodsId);
            //构建相关数据
            String uuid = UUID.randomUUID().toString();
            CorrelationData correlationData = new CorrelationData(uuid);//消息唯一id,用于区分消息
            Message returnMessage = MessageBuilder.withBody(objectMapper.writeValueAsBytes(message))
                    .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                    .setDeliveryMode(MessageDeliveryMode.PERSISTENT)
                    .setReceivedExchange(RabbitConstant.DELETE_GOODS_FROM_ES_EXCHANGE)
                    .setReceivedRoutingKey(RabbitConstant.DELETE_GOODS_FROM_ES_KEY)
                    .build();
            correlationData.setReturnedMessage(returnMessage);

            //先记录到日志，用于后续发消息失败，有兜底方案
            RabbitMQSendLogDTO mqSendLogDTO = new RabbitMQSendLogDTO();
            mqSendLogDTO.setMessageId(uuid);
            mqSendLogDTO.setExchange(RabbitConstant.DELETE_GOODS_FROM_ES_EXCHANGE);
            mqSendLogDTO.setRouteKey(RabbitConstant.DELETE_GOODS_FROM_ES_KEY);
            mqSendLogDTO.setSendContent(JSONUtil.toJsonStr(message));
            feignRabbitMQSendLogService.save(mqSendLogDTO);

            // 指定之前定义的延迟交换机名 与路由键名
            rabbitTemplate.convertAndSend(RabbitConstant.DELETE_GOODS_FROM_ES_EXCHANGE,
                    RabbitConstant.DELETE_GOODS_FROM_ES_KEY, message, correlationData);
        }
    }
}
