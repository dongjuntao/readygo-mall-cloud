package com.mall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.common.base.utils.PageUtil;
import com.mall.order.entity.OrderEntity;
import com.mall.order.entity.TradeEntity;
import com.mall.order.vo.TradeDetailVO;

import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 订单service
 * @Date 2022/6/16 19:40
 * @Version 1.0
 */
public interface OrderService extends IService<OrderEntity> {
    /**
     *
     * @param pageNum
     * @param pageSize
     * @param code 订单号
     * @param status 订单状态
     * @return
     */
    PageUtil queryPage(Integer pageNum,Integer pageSize, Long memberId, String code,String status);

    /**
     * 保存订单（创建订单和子订单）
     * @param tradeEntity
     * @param tradeDetailVO
     * @return
     */
    void saveOrder(TradeEntity tradeEntity, TradeDetailVO tradeDetailVO);

    /**
     * 根据交易id修改订单状态(批量)
     * @param tradeId
     */
    void updateOrderStatusByTrade(Long tradeId, String orderStatus);

    /**
     * 获取订单表信息
     * @return
     */
    OrderEntity getOrderByParams(String code);

    /**
     * 获取订单表信息（包括订单明细）
     * @param code
     * @return
     */
    OrderEntity getOrderAndDetailByParams(String code);

    /**
     * 修改订单状态
     * @param code
     * @param orderStatus
     */
    void updateOrderStatus(String code, String orderStatus);

    /**
     * 取消订单状态
     * @param code
     * @param cancelReason
     */
    void cancelOrder(String code, String cancelReason);

    /**
     * 删除订单
     * @param code 订单号
     */
    void deleteOrder(String code);
}
