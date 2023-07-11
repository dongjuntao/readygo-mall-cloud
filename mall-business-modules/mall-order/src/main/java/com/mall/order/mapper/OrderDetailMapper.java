package com.mall.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.order.entity.OrderDetailEntity;
import com.mall.order.vo.OrderSkuCountVO;
import com.mall.order.vo.RecentSixMonthOrderCountVO;
import org.apache.ibatis.annotations.Mapper;
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
@Mapper
public interface OrderDetailMapper extends BaseMapper<OrderDetailEntity> {

    List<OrderSkuCountVO> getSkuIdAndCountByTradeCode(String code);


    List<OrderSkuCountVO> getSkuIdAndCountByOrderCode(String code);

    /**
     * 订单数量查询
     * @return
     */
    int count(@Param("startTime") String startTime,
              @Param("endTime") String endTime,
              @Param("status") String status,
              @Param("merchantId") Long merchantId);

    /**
     * 销售额统计
     * @return
     */
    BigDecimal salesVolume(@Param("startTime") String startTime,
                           @Param("endTime") String endTime,
                           @Param("status") String status,
                           @Param("merchantId") Long merchantId);

    /**
     * 近六个月的订单量
     * @return
     */
    List<RecentSixMonthOrderCountVO> getRecentSixMonthOrderCount(@Param("merchantId") Long merchantId);
}
