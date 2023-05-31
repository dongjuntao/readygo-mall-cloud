package com.mall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.order.entity.TradeEntity;
import com.mall.order.vo.TradeDetailVO;

import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 交易Service
 * @Date 2022/6/18 22:05
 * @Version 1.0
 */
public interface TradeService extends IService<TradeEntity> {

    /**
     * 创建交易（创建订单和子订单）
     * @param tradeDetailVO
     * @return
     */
    TradeEntity createTrade(TradeDetailVO tradeDetailVO);

    TradeEntity getTradeByParams(String code);

    int updateTradeStatus(String code, String tradeStatus);

    /**
     * 根据查询查询交易信息（包括订单信息）
     * @return
     */
    TradeEntity getTradeDetailByParams(String code);
}
