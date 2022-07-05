package com.mall.order.api.feign;

import com.mall.common.base.CommonResult;
import com.mall.common.base.config.FeignConfig;
import com.mall.common.base.constant.ServiceNameConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 交易接口
 * @Date 2022/6/28 15:39
 * @Version 1.0
 */
@FeignClient(value = ServiceNameConstant.MALL_ORDER,configuration = FeignConfig.class)
@RequestMapping(value = "trade")
public interface FeignTradeService {

    @GetMapping("tradeInfo")
    CommonResult tradeInfo(@RequestParam Map<String, Object> params);
}
