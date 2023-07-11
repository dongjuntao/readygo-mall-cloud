package com.mall.order.api.feign;

import com.mall.common.base.CommonResult;
import com.mall.common.base.config.FeignConfig;
import com.mall.common.base.constant.ServiceNameConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2023/7/7 21:07
 * @Version 1.0
 */
@FeignClient(value = ServiceNameConstant.MALL_ORDER,configuration = FeignConfig.class)
@RequestMapping(value = "back/order")
public interface FeignBackOrderService {

    /**
     * 订单数量统计
     * @return
     */
    @GetMapping("count")
    CommonResult count(@RequestParam(value = "startTime", required = false) String startTime,
                       @RequestParam(value = "endTime", required = false) String endTime,
                       @RequestParam(value = "status", required = false) String status,
                       @RequestParam(value = "merchantId", required = false) Long merchantId);

    @GetMapping("salesVolume")
    CommonResult salesVolume(@RequestParam(value = "startTime", required = false) String startTime,
                             @RequestParam(value = "endTime", required = false) String endTime,
                             @RequestParam(value = "status", required = false) String status,
                             @RequestParam(value = "merchantId", required = false) Long merchantId);

    /**
     * 销售额统计
     * @return
     */
    @GetMapping("recentSixMonthOrderCount")
    CommonResult recentSixMonthOrderCountList(@RequestParam(value = "merchantId", required = false) Long merchantId);
}
