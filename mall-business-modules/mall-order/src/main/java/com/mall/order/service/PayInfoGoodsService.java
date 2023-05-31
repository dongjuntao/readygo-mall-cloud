package com.mall.order.service;

import com.mall.order.vo.PayVO;

import java.util.Map;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2023/5/29 15:12
 * @Version 1.0
 */
public interface PayInfoGoodsService {

    /**
     * 获取结算页信息【商品信息】
     */
    PayVO getPayInfoGoods(Map<String, Object> params);
}
