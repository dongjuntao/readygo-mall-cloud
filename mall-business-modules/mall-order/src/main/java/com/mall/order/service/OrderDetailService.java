package com.mall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.order.entity.OrderDetailEntity;
import com.mall.order.enums.AfterSalesStatusEnum;
import com.mall.order.enums.SubOrderStatusEnum;
import com.mall.order.vo.OrderSkuCountVO;
import com.mall.order.vo.RecentSixMonthOrderCountVO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author DongJunTao
 * @Description 订单详情service
 * @Date 2022/6/16 19:41
 * @Version 1.0
 */
public interface OrderDetailService extends IService<OrderDetailEntity> {

    List<OrderSkuCountVO> getSkuIdAndCountByTradeCode(String code);

    List<OrderSkuCountVO> getSkuIdAndCountByOrderCode(String code);

    void updateAfterSalesStatus(Long orderDetailId, AfterSalesStatusEnum afterSalesStatus);

    void updateAfterSalesStatusByOrderId(Long orderId, AfterSalesStatusEnum afterSalesStatus);

    void updateSubStatusByOrderId(Long orderId, SubOrderStatusEnum subOrderStatusEnum);

    void updateSubStatus(Long orderDetailId, SubOrderStatusEnum subOrderStatusEnum);

    /**
     * 订单数量查询
     * @return
     */
    int count(String startTime, String endTime,String status, Long merchantId);

    /**
     * 销售额统计
     * @return
     */
    BigDecimal salesVolume(String startTime, String endTime, String status, Long merchantId);

    /**
     * 近六个月的订单量
     * @return
     */
    List<RecentSixMonthOrderCountVO> getRecentSixMonthOrderCount(Long merchantId);

    OrderDetailEntity getOrderDetailByParams(String subCode);
}
