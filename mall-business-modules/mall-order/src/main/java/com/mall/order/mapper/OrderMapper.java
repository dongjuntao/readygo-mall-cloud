package com.mall.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.order.entity.OrderEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author DongJunTao
 * @Description 订单service
 * @Date 2022/6/16 19:40
 * @Version 1.0
 */
@Mapper
public interface OrderMapper extends BaseMapper<OrderEntity> {
}
