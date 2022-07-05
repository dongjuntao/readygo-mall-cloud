package com.mall.order.api.feign;

import com.mall.common.base.CommonResult;
import com.mall.common.base.config.FeignConfig;
import com.mall.common.base.constant.ServiceNameConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2022/7/1 17:01
 * @Version 1.0
 */
@FeignClient(value = ServiceNameConstant.MALL_ORDER,configuration = FeignConfig.class)
@RequestMapping(value = "couponSelected")
public interface FeignCouponSelectedService {
    /**
     * 获取用户已选中的优惠券
     * @return
     */
    @GetMapping("getSelected")
    CommonResult getSelected(@RequestHeader("currentUserInfo") String currentUserInfo);
}
