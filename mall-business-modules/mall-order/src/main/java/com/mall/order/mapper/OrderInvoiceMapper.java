package com.mall.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.order.entity.OrderInvoiceEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author DongJunTao
 * @Description 订单-发票service
 * @Date 2022/6/16 14:41
 * @Version 1.0
 */
@Mapper
public interface OrderInvoiceMapper extends BaseMapper<OrderInvoiceEntity> {
}
