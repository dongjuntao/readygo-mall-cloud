package com.mall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.common.base.utils.PageUtil;
import com.mall.member.entity.CouponReceivedEntity;

import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 我的优惠券service
 * @Date 2022/6/4 15:20
 * @Version 1.0
 */
public interface CouponReceivedService extends IService<CouponReceivedEntity> {

    int saveCouponReceived(CouponReceivedEntity couponReceived);

    PageUtil getByPage(Integer pageNum, Integer pageSize, Long memberId, Integer useStatus);

    Integer count(Map<String, Object> params);

    List<CouponReceivedEntity> getListAll(Map<String, Object> params);

    CouponReceivedEntity getById(Long id);

    int updateUseStatus(Long receivedCouponId, Integer status);

    PageUtil queryPageWithMemberInfo(Integer pageNum, Integer pageSize, Long couponId);
}
