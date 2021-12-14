package com.mall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.common.util.PageUtil;
import com.mall.coupon.entity.CouponEntity;

import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 优惠券service
 * @Date 2021/10/31 22:03
 * @Version 1.0
 */
public interface CouponService extends IService<CouponEntity> {

    /**
     * 保存优惠券
     * @param couponEntity
     * @return
     */
    int saveCoupon(CouponEntity couponEntity);

    /**
     * 更新优惠券
     * @param couponEntity
     * @return
     */
    int updateCoupon(CouponEntity couponEntity);

    /**
     * 分页查询优惠券列表
     * @param params
     * @return
     */
    PageUtil getByPage(Map<String, Object> params);

    CouponEntity getById(Long couponId);

    /**
     * 删除优惠券
     * @param couponIds
     */
    void deleteBatch(Long[] couponIds);

    int updateStatus(Long couponId, Boolean status);
}
