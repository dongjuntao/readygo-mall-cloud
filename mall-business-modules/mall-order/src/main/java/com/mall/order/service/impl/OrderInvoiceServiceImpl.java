package com.mall.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.order.entity.OrderInvoiceEntity;
import com.mall.order.mapper.OrderInvoiceMapper;
import com.mall.order.service.OrderInvoiceService;
import org.springframework.stereotype.Service;

/**
 * @Author DongJunTao
 * @Description  订单-发票service实现
 * @Date 2022/6/16 19:47
 * @Version 1.0
 */
@Service
public class OrderInvoiceServiceImpl extends ServiceImpl<OrderInvoiceMapper, OrderInvoiceEntity> implements OrderInvoiceService {
}
