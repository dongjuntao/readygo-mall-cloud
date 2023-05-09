package com.mall.cart.aspect;

import com.mall.cart.constant.RedisKeyConstant;
import com.mall.common.base.constant.RabbitConstant;
import com.mall.common.base.utils.CurrentUserContextUtil;
import com.mall.common.seata.util.RedisUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

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
    private Object sendFootprint(ProceedingJoinPoint joinPoint) throws Throwable{
        Object[] args = joinPoint.getArgs();
        //当前登录用户id
        Long memberId = CurrentUserContextUtil.getCurrentUserInfo().getUserId();
        //第一次 删除 redis缓存
        RedisUtil.hDel(RedisKeyConstant.CART_KEY, String.valueOf(memberId));
        //执行目标方法
        Object object = joinPoint.proceed(args);
        //第二次需要延迟删除，发送到 rabbitmq 进行延迟删除，若删除失败，rabbitmq进行重试
        Map<String, Object> messageBody = new HashMap<>();
        messageBody.put("memberId",memberId);
        // 指定之前定义的延迟交换机名 与路由键名
        rabbitTemplate.convertAndSend(RabbitConstant.CART_DOUBLE_DELETE_DELAY_EXCHANGE,
                RabbitConstant.CART_DOUBLE_DELETE_DELAY_KEY, messageBody);

        return object;

    }

}
