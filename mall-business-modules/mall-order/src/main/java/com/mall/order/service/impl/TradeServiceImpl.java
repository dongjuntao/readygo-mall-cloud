package com.mall.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.order.entity.TradeEntity;
import com.mall.order.mapper.TradeMapper;
import com.mall.order.service.TradeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 交易service实现
 * @Date 2022/6/18 22:06
 * @Version 1.0
 */
@Service
public class TradeServiceImpl extends ServiceImpl<TradeMapper, TradeEntity> implements TradeService {

    @Override
    public TradeEntity getTradeByParams(Map<String, Object> params) {
        String code = params.get("code") == null ? null: params.get("code").toString();
        QueryWrapper<TradeEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(!StringUtils.isEmpty(code), "code", code);
        return baseMapper.selectOne(queryWrapper);
    }
}
