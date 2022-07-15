package com.mall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.order.entity.OrderDetailEntity;
import com.mall.order.vo.OrderSkuCountVO;

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
}
