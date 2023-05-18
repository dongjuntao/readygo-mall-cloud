package com.mall.order.aspect;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mall.cart.api.feign.FeignCartService;
import com.mall.common.base.constant.RabbitConstant;
import com.mall.common.base.utils.CurrentUserContextUtil;
import com.mall.log.api.dto.RabbitMQSendLogDTO;
import com.mall.log.api.feign.FeignRabbitMQSendLogService;
import com.mall.member.api.FeignCouponReceivedService;
import com.mall.order.entity.CouponSelectedEntity;
import com.mall.order.entity.TradeEntity;
import com.mall.order.service.CouponSelectedService;
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
 * @Description 订单创建成功后，删除购物车信息
 * @Date 2022/6/26 16:22
 * @Version 1.0
 */
@Component
@Aspect
public class CreateTradeAspect {

    @Autowired
    private FeignCartService feignCartService;

    @Autowired
    private CouponSelectedService couponSelectedService;

    @Autowired
    private FeignCouponReceivedService feignCouponReceivedService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FeignRabbitMQSendLogService feignRabbitMQSendLogService;

    /**
     * 切点【提交订单】
     */
    @Pointcut("execution(* com.mall.order.service.TradeService.createTrade(..))")
    private void createTradeAspect() {}

    /**
     * 提交订单后
     * 1.删除购物车信息
     * 2.修改用户优惠券使用状态，此时为冻结状态
     * 3.再删除订单页已选的优惠券记录
     * @param returnVal 返回值
     */
    @AfterReturning(value = "createTradeAspect()", returning = "returnVal")
    private void deleteCartInfo(Object returnVal) throws JsonProcessingException {
        if (returnVal != null) {
            Long memberId = CurrentUserContextUtil.getCurrentUserInfo().getUserId();
            TradeEntity trade = (TradeEntity) returnVal;
            if (trade.getMemberId().equals(memberId)) {
                //删除购物车信息
                feignCartService.deleteCart(2,null, JSON.toJSONString(CurrentUserContextUtil.getCurrentUserInfo()));
                CouponSelectedEntity couponSelected= couponSelectedService.getSelected(memberId);
                if (couponSelected != null) {
                    //修改用户优惠券使用状态，此时为冻结状态
                    feignCouponReceivedService.updateUseStatus(couponSelected.getReceivedCouponId(), 3);
                    //再删除订单页已选的优惠券记录【注意：这里是逻辑删除，将其置为‘删除状态’，支付后，将其物理删除】
                    couponSelectedService.setIsDel(memberId);
                }

                //创建订单后，保存信息到消息队列，30分钟不支付，则取消订单
                Map<String, Object> message = new HashMap<>();
                String tradeCode = trade.getCode(); //交易编号
                message.put("tradeCode",tradeCode);

                //构建相关数据
                String uuid = UUID.randomUUID().toString();
                CorrelationData correlationData = new CorrelationData(uuid);//消息唯一id,用于区分消息
                Message returnMessage = MessageBuilder.withBody(objectMapper.writeValueAsBytes(message))
                        .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                        .setDeliveryMode(MessageDeliveryMode.PERSISTENT)
                        .setReceivedExchange(RabbitConstant.ORDER_AUTO_CANCEL_DELAY_EXCHANGE)
                        .setReceivedRoutingKey(RabbitConstant.ORDER_AUTO_CANCEL_DELAY_KEY)
                        .build();
                correlationData.setReturnedMessage(returnMessage);

                //先记录到日志，用于后续发消息失败，有兜底方案
                RabbitMQSendLogDTO mqSendLogDTO = new RabbitMQSendLogDTO();
                mqSendLogDTO.setMessageId(uuid);
                mqSendLogDTO.setExchange(RabbitConstant.ORDER_AUTO_CANCEL_DELAY_EXCHANGE);
                mqSendLogDTO.setRouteKey(RabbitConstant.ORDER_AUTO_CANCEL_DELAY_KEY);
                mqSendLogDTO.setSendContent(JSONUtil.toJsonStr(message));
                feignRabbitMQSendLogService.save(mqSendLogDTO);

                // 指定之前定义的延迟交换机名 与路由键名
                rabbitTemplate.convertAndSend(RabbitConstant.ORDER_AUTO_CANCEL_DELAY_EXCHANGE,
                        RabbitConstant.ORDER_AUTO_CANCEL_DELAY_KEY, message, correlationData);
            }
        }
    }

}
