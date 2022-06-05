package com.mall.cart.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.cart.entity.CartCouponSelectedEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author DongJunTao
 * @Description 购物车中已选中的优惠券Mapper
 * @Date 2022/6/5 15:26
 * @Version 1.0
 */
@Mapper
public interface CartCouponSelectedMapper extends BaseMapper<CartCouponSelectedEntity> {
}
