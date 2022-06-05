package com.mall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.common.base.utils.PageUtil;
import com.mall.coupon.entity.CouponEntity;

import java.util.List;
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

    /**
     * 修改优惠券状态
     * @param couponId 优惠券id
     * @param status 状态
     * @return
     */
    int updateStatus(Long couponId, Boolean status);

    /**
     * 优惠券审核
     * @param couponId
     * @param authStatus
     * @param authOpinion
     * @return
     */
    int auth(Long couponId, Integer authStatus, String authOpinion);

    List<CouponEntity> getBatchByIds(Long[] ids);
}
