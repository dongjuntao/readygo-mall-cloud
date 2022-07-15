package com.mall.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.common.base.utils.CurrentUserContextUtil;
import com.mall.order.entity.TradeEntity;
import com.mall.order.enums.CodePrefixEnum;
import com.mall.order.enums.OrderStatusEnum;
import com.mall.order.enums.TradeStatusEnum;
import com.mall.order.mapper.TradeMapper;
import com.mall.order.service.OrderService;
import com.mall.order.service.TradeService;
import com.mall.order.util.SnowFlakeUtil;
import com.mall.order.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 交易service实现
 * @Date 2022/6/18 22:06
 * @Version 1.0
 */
@Service
public class TradeServiceImpl extends ServiceImpl<TradeMapper, TradeEntity> implements TradeService {

    @Autowired
    private TradeMapper tradeMapper;

    @Autowired
    private OrderService orderService;

    @Override
    @Transactional
    public TradeEntity createTrade(TradeDetailVO tradeDetailVO) {
        PayVO payVO = tradeDetailVO.getPayVO(); //商家、商品、及支付相关数据
        TradeEntity tradeEntity = new TradeEntity(); //交易
        tradeEntity.setCode(SnowFlakeUtil.getSnowFlakeId(CodePrefixEnum.T,1,1));
        tradeEntity.setTotalPrice(payVO.getTotalPrice());
        tradeEntity.setFreight(payVO.getTotalFreight());
        tradeEntity.setFinalPrice(payVO.getFinalPrice());
        tradeEntity.setMemberId(CurrentUserContextUtil.getCurrentUserInfo().getUserId());
        tradeEntity.setMemberName(CurrentUserContextUtil.getCurrentUserInfo().getUserName());
        tradeEntity.setTradeTime(new Date());
        tradeEntity.setPayStatus(TradeStatusEnum.UNPAID);
        tradeMapper.insert(tradeEntity);
        //保存订单信息
        orderService.saveOrder(tradeEntity, tradeDetailVO);
        //创建交易成功后，删除购物车信息
        return tradeEntity;
    }

    @Override
    public TradeEntity getTradeByParams(Map<String, Object> params) {
        String code = params.get("code") == null ? null: params.get("code").toString();
        QueryWrapper<TradeEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(!StringUtils.isEmpty(code), "code", code);
        return baseMapper.selectOne(queryWrapper);
    }

    /**
     * 修改交易状态 且 修改订单状态
     * @param code
     */
    @Override
    @Transactional
    public int updateTradeStatus(String code, String tradeStatus) {
        QueryWrapper<TradeEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(!StringUtils.isEmpty(code), "code", code);
        TradeEntity trade = baseMapper.selectOne(queryWrapper);
        if (trade != null) {
            //修改交易付款状态
            System.out.println("TradeStatusEnum=====tradeStatus======"+TradeStatusEnum.valueOf(tradeStatus));
            trade.setPayStatus(TradeStatusEnum.valueOf(tradeStatus));
            baseMapper.updateById(trade);
            orderService.updateOrderStatusByTrade(trade.getId(), tradeStatus);
        }
        return 1;
    }

    /**
     * 获取交易信息详情（包括订单信息）
     * @param params
     * @return
     */
    @Override
    public TradeEntity getTradeDetailByParams(Map<String, Object> params) {
        String code = params.get("code") == null ? null: params.get("code").toString();
        return tradeMapper.getTradeDetailByCode(code);
    }
}
