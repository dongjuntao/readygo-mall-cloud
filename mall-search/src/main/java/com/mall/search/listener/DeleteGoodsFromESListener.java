package com.mall.search.listener;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mall.common.base.CommonResult;
import com.mall.common.base.constant.RabbitConstant;
import com.mall.goods.api.FeignGoodsService;
import com.mall.log.api.dto.RabbitMQReceivedLogDTO;
import com.mall.log.api.feign.FeignRabbitMQReceivedLogService;
import com.mall.search.entity.ESGoods;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 商品上架审核通过后 监听商品数据 保存到ES
 * @Date 2023/6/21 21:39
 * @Version 1.0
 */
@Component
public class DeleteGoodsFromESListener {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FeignRabbitMQReceivedLogService feignRabbitMQReceivedLogService;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    /**
     * 监听，从ES中删除商品
     */
//    @RabbitListener(queues = RabbitConstant.DELETE_GOODS_FROM_ES_QUEUE)
    @RabbitListener(bindings = {
            @QueueBinding(
                    exchange = @Exchange(RabbitConstant.DELETE_GOODS_FROM_ES_EXCHANGE),
                    key = RabbitConstant.DELETE_GOODS_FROM_ES_KEY,
                    value = @Queue(RabbitConstant.DELETE_GOODS_FROM_ES_QUEUE)
            )
    })
    public void deleteGoodsFromES(Map<String, Object> message, Channel channel, Message mqMessage) {
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

            //下架后删除ES中的商品
            String goodsId = message.get("goodsId").toString();
            if (goodsId != null) {
                elasticsearchRestTemplate.delete(goodsId, ESGoods.class);
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
            }catch (Exception e2) {}
        }
    }
}
