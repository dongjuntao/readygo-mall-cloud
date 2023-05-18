package com.mall.goods.aspect;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mall.common.base.CommonResult;
import com.mall.common.base.constant.RabbitConstant;
import com.mall.common.base.utils.CurrentUserContextUtil;
import com.mall.goods.entity.GoodsEntity;
import com.mall.log.api.dto.RabbitMQSendLogDTO;
import com.mall.log.api.feign.FeignRabbitMQSendLogService;
import lombok.extern.slf4j.Slf4j;
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
 * @Description 用户足迹记录
 * @Date 2022/4/21 16:22
 * @Version 1.0
 */
@Component
@Aspect
@Slf4j
public class FootprintAspect {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private FeignRabbitMQSendLogService feignRabbitMQSendLogService;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 切点【前台用户点击到某一商品时需要记录足迹】
     */
    @Pointcut("execution(* com.mall.goods.controller.FrontGoodsController.getGoodsById(..))")
    private void footprintAspect() {}

    /**
     * 拦截controller,发送消息通知
     * @param returnVal
     */
    @AfterReturning(value = "footprintAspect()", returning = "returnVal")
    private void sendFootprint(CommonResult returnVal) throws JsonProcessingException {
        Long goodsId = null;
        Long merchantId = null;
        if (returnVal != null && "200".equals(returnVal.getCode())) {
            GoodsEntity goodsEntity = (GoodsEntity) returnVal.getData();
            goodsId = goodsEntity.getId();
            merchantId = goodsEntity.getAdminUserId();
        }
        //先判断用户是否登录，若已登录，再需要发送消息
        if (CurrentUserContextUtil.getCurrentUserInfo() != null) {
            Long userId = CurrentUserContextUtil.getCurrentUserInfo().getUserId();
            Map<String, Object> message = new HashMap<>();
            message.put("userId",userId);
            message.put("goodsId",goodsId);
            message.put("merchantId", merchantId);

            //构建相关数据
            String uuid = UUID.randomUUID().toString();
            CorrelationData correlationData = new CorrelationData(uuid);//消息唯一id,用于区分消息
            Message returnMessage = MessageBuilder.withBody(objectMapper.writeValueAsBytes(message))
                    .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                    .setDeliveryMode(MessageDeliveryMode.PERSISTENT)
                    .setReceivedExchange(RabbitConstant.MEMBER_FOOTPRINT_EXCHANGE)
                    .setReceivedRoutingKey(RabbitConstant.MEMBER_FOOTPRINT_KEY)
                    .build();
            correlationData.setReturnedMessage(returnMessage);

            //先记录到日志，用于后续发消息失败，有兜底方案
            RabbitMQSendLogDTO mqSendLogDTO = new RabbitMQSendLogDTO();
            mqSendLogDTO.setMessageId(uuid);
            mqSendLogDTO.setExchange(RabbitConstant.MEMBER_FOOTPRINT_EXCHANGE);
            mqSendLogDTO.setRouteKey(RabbitConstant.MEMBER_FOOTPRINT_KEY);
            mqSendLogDTO.setSendContent(JSONUtil.toJsonStr(message));
            feignRabbitMQSendLogService.save(mqSendLogDTO);
            //发送到rabbitmq
            rabbitTemplate.convertAndSend(RabbitConstant.MEMBER_FOOTPRINT_EXCHANGE,
                    RabbitConstant.MEMBER_FOOTPRINT_KEY,message,correlationData);

        }
    }

}
