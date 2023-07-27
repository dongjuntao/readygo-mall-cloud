package com.mall.order.api.feign;

import com.mall.common.base.CommonResult;
import com.mall.common.base.config.FeignConfig;
import com.mall.common.base.constant.ServiceNameConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    /**
     * 支付成功后修改状态
     * @param code 交易号
     * @return
     */
    @PostMapping("updateOrderStatus")
    CommonResult updateOrderStatus(@RequestParam("code") String code,
                                   @RequestParam("orderStatus") String orderStatus);

    @GetMapping("getTradeByParams")
    CommonResult getTradeByParams(@RequestParam Map<String, Object> params);

    @GetMapping("getTradeDetailByParams")
    CommonResult getTradeDetailByParams(@RequestParam Map<String, Object> params);

}
