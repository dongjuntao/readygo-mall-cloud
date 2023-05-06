package com.mall.order.aspect;

import com.alibaba.fastjson.JSON;
import com.mall.cart.api.feign.FeignCartService;
import com.mall.common.base.utils.CurrentUserContextUtil;
import com.mall.member.api.FeignCouponReceivedService;
import com.mall.order.constant.RabbitMQConstant;
import com.mall.order.entity.CouponSelectedEntity;
import com.mall.order.entity.OrderEntity;
import com.mall.order.entity.TradeEntity;
import com.mall.order.service.CouponSelectedService;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


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
    private void deleteCartInfo(Object returnVal){
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
                Map<String, Object> messageBody = new HashMap<>();
                String tradeCode = trade.getCode(); //交易编号
                messageBody.put("tradeCode",tradeCode);
                // 指定之前定义的延迟交换机名 与路由键名
                rabbitTemplate.convertAndSend(RabbitMQConstant.ORDER_AUTO_CANCEL_DELAY_EXCHANGE,
                        RabbitMQConstant.ORDER_AUTO_CANCEL_DELAY_KEY, messageBody);
            }
        }
    }

}
