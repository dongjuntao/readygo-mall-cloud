package com.mall.cart.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.cart.entity.CartCouponSelectedEntity;

import java.util.List;

/**
 * @Author DongJunTao
 * @Description 购物车中已选中的优惠券Service
 * @Date 2022/6/5 15:28
 * @Version 1.0
 */
public interface CartCouponSelectedService extends IService<CartCouponSelectedEntity> {

    int select(CartCouponSelectedEntity cartCouponSelected, Boolean use);

    List<CartCouponSelectedEntity> getSelected(Long memberId);
}
