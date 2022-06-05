package com.mall.member.api;

import com.mall.common.base.CommonResult;
import com.mall.common.base.config.FeignConfig;
import com.mall.common.base.constant.ServiceNameConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author DongJunTao
 * @Description 我的优惠券
 * @Date 2022/6/5 17:11
 * @Version 1.0
 */
@FeignClient(value = ServiceNameConstant.MALL_MEMBER,configuration = FeignConfig.class)
@RequestMapping(value = "coupon/received")
public interface FeignCouponReceivedService {

    @GetMapping("getById")
    CommonResult getById(@RequestParam("receivedCouponId") Long receivedCouponId);
}
