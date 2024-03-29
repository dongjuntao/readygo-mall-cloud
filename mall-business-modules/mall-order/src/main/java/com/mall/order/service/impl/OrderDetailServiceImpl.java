package com.mall.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.order.entity.OrderDetailEntity;
import com.mall.order.entity.OrderEntity;
import com.mall.order.enums.AfterSalesStatusEnum;
import com.mall.order.enums.SubOrderStatusEnum;
import com.mall.order.mapper.OrderDetailMapper;
import com.mall.order.service.OrderDetailService;
import com.mall.order.vo.OrderSkuCountVO;
import com.mall.order.vo.RecentSixMonthOrderCountVO;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author DongJunTao
 * @Description 订单详情service实现
 * @Date 2022/6/16 19:46
 * @Version 1.0
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetailEntity> implements OrderDetailService {

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Override
    @Transactional
    public List<OrderSkuCountVO> getSkuIdAndCountByTradeCode(String code) {
        return orderDetailMapper.getSkuIdAndCountByTradeCode(code);
    }

    @Override
    @Transactional
    public List<OrderSkuCountVO> getSkuIdAndCountByOrderCode(String code) {
        return orderDetailMapper.getSkuIdAndCountByOrderCode(code);
    }

    @Override
    public void updateAfterSalesStatus(Long orderDetailId, AfterSalesStatusEnum afterSalesStatus) {
        QueryWrapper<OrderDetailEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(orderDetailId != null, "id", orderDetailId);
        OrderDetailEntity orderDetail = baseMapper.selectOne(queryWrapper);
        if (orderDetail != null) {
            orderDetail.setAfterSalesStatus(afterSalesStatus);
            baseMapper.updateById(orderDetail);
        }
    }

    /**
     * 根据订单id，批量修改售后状态
     * @param orderId
     * @param afterSalesStatus
     */
    @Override
    public void updateAfterSalesStatusByOrderId(Long orderId, AfterSalesStatusEnum afterSalesStatus) {
        QueryWrapper<OrderDetailEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(orderId != null, "order_id", orderId);
        List<OrderDetailEntity> orderDetailList = baseMapper.selectList(queryWrapper);
        if (!CollectionUtils.isEmpty(orderDetailList)) {
            for (OrderDetailEntity orderDetail : orderDetailList) {
                orderDetail.setAfterSalesStatus(afterSalesStatus);
            }
            this.updateBatchById(orderDetailList);
        }
    }

    /**
     * 根据订单id，批量修改子订单状态
     * @param orderId
     * @param subOrderStatusEnum
     */
    @Override
    public void updateSubStatusByOrderId(Long orderId, SubOrderStatusEnum subOrderStatusEnum) {
        QueryWrapper<OrderDetailEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(orderId != null, "order_id", orderId);
        List<OrderDetailEntity> orderDetailList = baseMapper.selectList(queryWrapper);
        if (!CollectionUtils.isEmpty(orderDetailList)) {
            for (OrderDetailEntity orderDetail : orderDetailList) {
                orderDetail.setSubStatus(subOrderStatusEnum);
            }
            this.updateBatchById(orderDetailList);
        }
    }

    @Override
    public void updateSubStatus(Long orderDetailId, SubOrderStatusEnum subOrderStatusEnum) {
        QueryWrapper<OrderDetailEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(orderDetailId != null, "id", orderDetailId);
        OrderDetailEntity orderDetail = baseMapper.selectOne(queryWrapper);
        if (orderDetail != null) {
            orderDetail.setSubStatus(subOrderStatusEnum);
            baseMapper.updateById(orderDetail);
        }
    }

    /**
     * 订单数量
     * @param startTime
     * @param endTime
     * @param status
     * @return
     */
    @Override
    public int count(String startTime, String endTime, String status, Long merchantId) {
        return baseMapper.count(startTime, endTime, status, merchantId);
    }

    /**
     * 销售额
     * @param startTime
     * @param endTime
     * @param status
     * @return
     */
    @Override
    public BigDecimal salesVolume(String startTime, String endTime, String status, Long merchantId) {
        return baseMapper.salesVolume(startTime, endTime, status, merchantId);
    }

    /**
     * 近六个月的订单量
     * @return
     */
    @Override
    public List<RecentSixMonthOrderCountVO> getRecentSixMonthOrderCount(Long merchantId) {
        return baseMapper.getRecentSixMonthOrderCount(merchantId);
    }

    @Override
    public OrderDetailEntity getOrderDetailByParams(String subCode) {
        QueryWrapper<OrderDetailEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(!StringUtils.isEmpty(subCode), "sub_code", subCode);
        return baseMapper.selectOne(queryWrapper);
    }
}
