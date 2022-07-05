package com.mall.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.order.entity.OrderDetailEntity;
import com.mall.order.mapper.OrderDetailMapper;
import com.mall.order.service.OrderDetailService;
import com.mall.order.vo.OrderSkuCountVO;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @GlobalTransactional
    @Transactional
    public List<OrderSkuCountVO> getSkuIdAndCount(String code) {
        return orderDetailMapper.getSkuIdAndCount(code);
    }
}
