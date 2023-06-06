package com.mall.member.consumer;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mall.common.base.CommonResult;
import com.mall.common.base.constant.KafkaConstant;
import com.mall.common.base.constant.RabbitConstant;
import com.mall.log.api.dto.RabbitMQReceivedLogDTO;
import com.mall.log.api.feign.FeignRabbitMQReceivedLogService;
import com.mall.member.entity.FootprintEntity;
import com.mall.member.service.FootprintService;
import com.rabbitmq.client.Channel;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 接收商品服务推来的消息
 * @Date 2022/4/22 9:26
 * @Version 1.0
 */
@Component
public class FootprintConsumer {

    @Autowired
    private FootprintService footprintService;

    @KafkaListener(topics = { KafkaConstant.FOOTPRINT_TOPIC })
    public void listener(ConsumerRecord<String, String> record, Acknowledgment acknowledgment) {
        String message = record.value();
        Map<String,Object> messageMap = JSONUtil.toBean(message, Map.class) ;

        //先查询该用户是否已经浏览过该商品，如果否，创建记录，如果没有，修改时间
        FootprintEntity footprint = footprintService.getFootprintByParams(messageMap);
        if (footprint == null) {
            footprint = new FootprintEntity();
            footprint.setMemberId(Long.valueOf(String.valueOf(messageMap.get("userId"))));
            footprint.setGoodsId(Long.valueOf(String.valueOf(messageMap.get("goodsId"))));
            footprint.setMerchantId(Long.valueOf(String.valueOf(messageMap.get("merchantId"))));
            footprint.setCreateTime(new Date());
        } else {
            footprint.setUpdateTime(new Date());
        }
        //更新到数据库
        footprintService.saveOrUpdate(footprint);

        acknowledgment.acknowledge();
    }
}
