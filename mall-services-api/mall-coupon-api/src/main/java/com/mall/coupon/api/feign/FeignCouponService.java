package com.mall.coupon.api.feign;

import com.mall.common.base.CommonResult;
import com.mall.common.base.config.FeignConfig;
import com.mall.common.base.constant.ServiceNameConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author DongJunTao
 * @Description 优惠券api
 * @Date 2022/6/4 15:47
 * @Version 1.0
 */
@FeignClient(value = ServiceNameConstant.MALL_COUPON,configuration = FeignConfig.class)
@RequestMapping(value = "coupon")
public interface FeignCouponService {

    @GetMapping("getCouponById")
    CommonResult getCouponById(@RequestParam("couponId") Long couponId);

}
