package com.mall.order.consumer;

import com.mall.common.base.constant.RabbitConstant;
import com.mall.common.base.utils.MapUtil;
import com.mall.order.entity.OrderEntity;
import com.mall.order.entity.TradeEntity;
import com.mall.order.enums.OrderStatusEnum;
import com.mall.order.service.OrderService;
import com.mall.order.service.TradeService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 订单超时未支付自动取消 RabbitMQ
 * @Date 2023/5/6 19:26
 * @Version 1.0
 */
@Component
public class OrderAutoCancelConsumer {

    @Autowired
    private OrderService orderService;

    @Autowired
    private TradeService tradeService;
    /**
     * 订单超时未支付，自动取消
     * @param message
     */
    @RabbitListener(queues = RabbitConstant.ORDER_AUTO_CANCEL_DEAD_QUEUE)
    public void process(Map<String, Object> message) {
        String tradeCode =  String.valueOf(message.get("tradeCode"));
        if (!Strings.isEmpty(tradeCode)) {
            TradeEntity trade = tradeService.getTradeDetailByParams(new MapUtil().put("code", tradeCode));
            List<OrderEntity> orderList = trade.getOrderList();
            if (!CollectionUtils.isEmpty(orderList)) {
                for (OrderEntity order : orderList) {
                    //如果订单有未支付的，则取消
                    if (OrderStatusEnum.UNPAID.equals(order.getStatus())) {
                        orderService.updateOrderStatus(order.getCode(),OrderStatusEnum.CANCELLED.toString());//取消订单
                        //TODO 处理优惠券，库存等
                    }
                }
            }
        }
    }

}
