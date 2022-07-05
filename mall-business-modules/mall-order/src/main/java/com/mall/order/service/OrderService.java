package com.mall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.order.entity.OrderEntity;
import com.mall.order.entity.TradeEntity;
import com.mall.order.vo.PayVO;
import com.mall.order.vo.TradeDetailVO;
import com.mall.order.vo.TradeVO;

/**
 * @Author DongJunTao
 * @Description 订单service
 * @Date 2022/6/16 19:40
 * @Version 1.0
 */
public interface OrderService extends IService<OrderEntity> {

    /**
     * 保存订单（创建订单和子订单）
     * @param tradeEntity
     * @param tradeDetailVO
     * @return
     */
    void saveOrder(TradeEntity tradeEntity, TradeDetailVO tradeDetailVO);

    void updateOrderStatus(Long tradeId);
}
