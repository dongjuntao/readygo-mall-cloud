package com.mall.cart.aspect;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mall.cart.constant.RedisKeyConstant;
import com.mall.common.base.constant.RabbitConstant;
import com.mall.common.base.utils.CurrentUserContextUtil;
import com.mall.common.seata.util.RedisUtil;
import com.mall.log.api.dto.RabbitMQSendLogDTO;
import com.mall.log.api.feign.FeignRabbitMQSendLogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
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
 * @Description redis双删 【购物车信息】
 * @Date 2022/4/21 16:22
 * @Version 1.0
 */
@Component
@Aspect
public class CartDoubleDeleteAspect {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FeignRabbitMQSendLogService feignRabbitMQSendLogService;

    /**
     * 切点【删除或更新购物车信息时触发】
     */
    @Pointcut("execution(* com.mall.cart.service.CartGoodsService.updateBatch(..)) || " +
            "execution(* com.mall.cart.service.CartService.deleteBatch(..)) || " +
            "execution(* com.mall.cart.service.CartGoodsService.delete(..)) || " +
            "execution(* com.mall.cart.service.CartService.saveCart(..))")
    private void cartDoubleDeleteAspect() {}

    /**
     * 拦截Service,发送双删通知
     * @param joinPoint
     */
    @Around(value = "cartDoubleDeleteAspect()")
    private Object sendFootprint(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        //当前登录用户id
        Long memberId = CurrentUserContextUtil.getCurrentUserInfo().getUserId();
        //第一次 删除 redis缓存
        RedisUtil.hDel(RedisKeyConstant.CART_KEY, String.valueOf(memberId));
        //执行目标方法
        Object object = joinPoint.proceed(args);
        //第二次需要延迟删除，发送到 rabbitmq 进行延迟删除，若删除失败，rabbitmq进行重试
        Map<String, Object> message = new HashMap<>();
        message.put("memberId",memberId);

        //构建相关数据
        String uuid = UUID.randomUUID().toString();
        CorrelationData correlationData = new CorrelationData(uuid);//消息唯一id,用于区分消息
        Message returnMessage = MessageBuilder.withBody(objectMapper.writeValueAsBytes(message))
                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .setDeliveryMode(MessageDeliveryMode.PERSISTENT)
                .setReceivedExchange(RabbitConstant.CART_DOUBLE_DELETE_DELAY_EXCHANGE)
                .setReceivedRoutingKey(RabbitConstant.CART_DOUBLE_DELETE_DELAY_KEY)
                .build();
        correlationData.setReturnedMessage(returnMessage);

        //先记录到日志，用于后续发消息失败，有兜底方案
        RabbitMQSendLogDTO mqSendLogDTO = new RabbitMQSendLogDTO();
        mqSendLogDTO.setMessageId(uuid);
        mqSendLogDTO.setExchange(RabbitConstant.CART_DOUBLE_DELETE_DELAY_EXCHANGE);
        mqSendLogDTO.setRouteKey(RabbitConstant.CART_DOUBLE_DELETE_DELAY_KEY);
        mqSendLogDTO.setSendContent(JSONUtil.toJsonStr(message));
        feignRabbitMQSendLogService.save(mqSendLogDTO);

        // 指定之前定义的延迟交换机名 与路由键名
        rabbitTemplate.convertAndSend(RabbitConstant.CART_DOUBLE_DELETE_DELAY_EXCHANGE,
                RabbitConstant.CART_DOUBLE_DELETE_DELAY_KEY, message, correlationData);

        return object;

    }

}
