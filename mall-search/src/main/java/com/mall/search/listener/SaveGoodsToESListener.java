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
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 商品上架审核通过后 监听商品数据 保存到ES
 * @Date 2023/6/20 19:39
 * @Version 1.0
 */
@Component
public class SaveGoodsToESListener {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FeignRabbitMQReceivedLogService feignRabbitMQReceivedLogService;

    @Autowired
    private FeignGoodsService feignGoodsService;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    /**
     * 监听，保存商品到ES
     */
    @RabbitListener(queues = RabbitConstant.SAVE_GOODS_TO_ES_QUEUE)
    public void saveGoodsToES(Map<String, Object> message, Channel channel, Message mqMessage) {
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

            //查询商品信息，保存到ES中
            Long goodsId =  Long.parseLong(message.get("goodsId").toString());
            CommonResult result = feignGoodsService.getGoodsById(goodsId);
            if (result != null && "200".equals(result.getCode())) {
                ESGoods esGoods = BeanUtil.mapToBean((Map<?, ?>) result.getData(), ESGoods.class, true);
                if (esGoods != null) {
                    elasticsearchRestTemplate.save(esGoods);
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
            }catch (Exception e2) {}
        }
    }
}
