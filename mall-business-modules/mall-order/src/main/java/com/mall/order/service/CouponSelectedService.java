package com.mall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.order.entity.CouponSelectedEntity;

import java.util.List;

/**
 * @Author DongJunTao
 * @Description 购物车中已选中的优惠券Service
 * @Date 2022/6/5 15:28
 * @Version 1.0
 */
public interface CouponSelectedService extends IService<CouponSelectedEntity> {

    int select(CouponSelectedEntity cartCouponSelected, Boolean use);

    List<CouponSelectedEntity> getSelected(Long memberId);
}
