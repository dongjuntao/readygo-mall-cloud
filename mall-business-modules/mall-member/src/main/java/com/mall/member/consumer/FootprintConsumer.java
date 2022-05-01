package com.mall.member.consumer;

import com.mall.common.base.constant.RabbitExchangeConstant;
import com.mall.common.base.constant.RabbitRoutingKeyConstant;
import com.mall.common.base.utils.CurrentUserContextUtil;
import com.mall.member.entity.FootprintEntity;
import com.mall.member.service.FootprintService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
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
@RabbitListener(bindings = @QueueBinding(
        value = @Queue(value = RabbitRoutingKeyConstant.FOOTPRINT_ROUTING_KEY,autoDelete="true"),
        exchange = @Exchange(value = RabbitExchangeConstant.FOOTPRINT_EXCHANGE,type = ExchangeTypes.TOPIC),
        key = RabbitRoutingKeyConstant.FOOTPRINT_ROUTING_KEY)
)
public class FootprintConsumer {

    @Autowired
    private FootprintService footprintService;
    /**
     * 监听是否有消息（是否需要记录足迹）
     * @param message
     */
    @RabbitHandler
    public void process(Map<String, Object> message) {
        //先查询该用户是否已经浏览过该商品，如果否，创建记录，如果没有，修改时间
        FootprintEntity footprint = footprintService.getFootprintByParams(message);
        if (footprint == null) {
            footprint = new FootprintEntity();
            footprint.setMemberId(Long.valueOf(String.valueOf(message.get("userId"))));
            footprint.setGoodsId(Long.valueOf(String.valueOf(message.get("goodsId"))));
            footprint.setCreateTime(new Date());
        } else {
            footprint.setUpdateTime(new Date());
        }
        //更新到数据库
        footprintService.saveOrUpdate(footprint);
    }
}
