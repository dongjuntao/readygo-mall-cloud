package com.mall.order.listener;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mall.common.base.CommonResult;
import com.mall.common.base.constant.RabbitConstant;
import com.mall.log.api.dto.RabbitMQReceivedLogDTO;
import com.mall.log.api.feign.FeignRabbitMQReceivedLogService;
import com.mall.order.entity.OrderEntity;
import com.mall.order.entity.TradeEntity;
import com.mall.order.enums.OrderStatusEnum;
import com.mall.order.service.OrderService;
import com.mall.order.service.TradeService;
import com.rabbitmq.client.Channel;
import org.apache.logging.log4j.util.Strings;
import org.springframework.amqp.core.Message;
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
public class OrderAutoCancelListener {

    @Autowired
    private OrderService orderService;

    @Autowired
    private TradeService tradeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FeignRabbitMQReceivedLogService feignRabbitMQReceivedLogService;

    /**
     * 订单超时未支付，自动取消
     * @param message
     */
    @RabbitListener(queues = RabbitConstant.ORDER_AUTO_CANCEL_DEAD_QUEUE)
    public void process(Map<String, Object> message, Channel channel, Message mqMessage) {
        RabbitMQReceivedLogDTO rabbitMQReceivedLogDTO = new RabbitMQReceivedLogDTO();
        try {
            //保存待消费的消息
            String exchange = mqMessage.getMessageProperties().getReceivedExchange();
            String routingKey = mqMessage.getMessageProperties().getReceivedRoutingKey();
            String consumerQueue  = mqMessage.getMessageProperties().getConsumerQueue();
            String correlationId  = String.valueOf(mqMessage.getMessageProperties().getHeaders()
                    .get("spring_returned_message_correlation"));
            Map<String,Object> returnMessage = objectMapper.readValue(mqMessage.getBody(), Map.class);
            rabbitMQReceivedLogDTO.setExchange(exchange);
            rabbitMQReceivedLogDTO.setMessageId(correlationId);
            rabbitMQReceivedLogDTO.setRouteKey(routingKey);
            rabbitMQReceivedLogDTO.setQueueName(consumerQueue);
            rabbitMQReceivedLogDTO.setReceivedContent(JSONUtil.toJsonStr(returnMessage));
            feignRabbitMQReceivedLogService.save(rabbitMQReceivedLogDTO);

            String tradeCode =  String.valueOf(message.get("tradeCode"));
            if (!Strings.isEmpty(tradeCode)) {
                TradeEntity trade = tradeService.getTradeDetailByParams(tradeCode);
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

            //确认
            channel.basicAck(mqMessage.getMessageProperties().getDeliveryTag(), false);

            //消费成功，修改数据库状态
            rabbitMQReceivedLogDTO.setStatus(1);
            feignRabbitMQReceivedLogService.update(rabbitMQReceivedLogDTO);
        } catch (Exception e) {
            try {
                //开始重试，达到既定次数，终止重试
                rabbitMQReceivedLogDTO.setStatus(2); //消费失败重试
                CommonResult result = feignRabbitMQReceivedLogService.update(rabbitMQReceivedLogDTO);
                if (result != null && "200".equals(result.getCode())) { //重试消费
                    channel.basicReject(mqMessage.getMessageProperties().getDeliveryTag(), true);
                } else {
                    channel.basicReject(mqMessage.getMessageProperties().getDeliveryTag(), false);
                }
            } catch (Exception e2) {}
        }
    }

}
