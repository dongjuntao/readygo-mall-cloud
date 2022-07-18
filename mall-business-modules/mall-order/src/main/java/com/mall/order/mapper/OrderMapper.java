package com.mall.order.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.order.entity.OrderEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 订单service
 * @Date 2022/6/16 19:40
 * @Version 1.0
 */
@Mapper
public interface OrderMapper extends BaseMapper<OrderEntity> {

    IPage<OrderEntity> queryPage(
            @Param("page") Page<OrderEntity> page,
            @Param(Constants.WRAPPER) QueryWrapper<OrderEntity> wrapper);

    OrderEntity getOrderAndDetail(OrderEntity orderEntity);
}
