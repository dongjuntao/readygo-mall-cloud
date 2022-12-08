package com.mall.goods.aspect;

import com.mall.common.base.CommonResult;
import com.mall.common.base.constant.RabbitExchangeConstant;
import com.mall.common.base.constant.RabbitRoutingKeyConstant;
import com.mall.common.base.utils.CurrentUserContextUtil;
import com.mall.goods.entity.GoodsEntity;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

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

    /**
     * 切点【前台用户点击到某一商品时需要记录足迹】
     */
    @Pointcut("execution(* com.mall.goods.controller.front.FrontGoodsController.getGoodsById(..))")
    private void footprintAspect() {}

    /**
     * 拦截controller,发送消息通知
     * @param returnVal
     */
    @AfterReturning(value = "footprintAspect()", returning = "returnVal")
    private void sendFootprint(CommonResult returnVal){
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
            rabbitTemplate.convertAndSend(RabbitExchangeConstant.FOOTPRINT_EXCHANGE,
                    RabbitRoutingKeyConstant. FOOTPRINT_ROUTING_KEY,message);
        }
    }

}
