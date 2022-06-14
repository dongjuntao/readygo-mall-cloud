package com.mall.cart.api.feign;

import com.mall.common.base.CommonResult;
import com.mall.common.base.config.FeignConfig;
import com.mall.common.base.constant.ServiceNameConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 购物车
 * @Date 2022/6/13 20:57
 * @Version 1.0
 */
@FeignClient(value = ServiceNameConstant.MALL_CART,configuration = FeignConfig.class)
@RequestMapping(value = "cart")
public interface FeignCartService {

    /**
     * 查询会员购物车信息
     * @param params
     * @return
     */
    @GetMapping("/list")
    CommonResult list(@RequestParam Map<String, Object> params,
                      @RequestHeader("currentUserInfo") String currentUserInfo);
}
