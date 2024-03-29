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
 * @Description
 * @Date 2022/6/30 22:24
 * @Version 1.0
 */
@FeignClient(value = ServiceNameConstant.MALL_ORDER,configuration = FeignConfig.class)
@RequestMapping(value = "order")
public interface FeignOrderService {

    /**
     * 支付成功后修改状态
     * @param code
     * @return
     */
    @PostMapping("updateOrderStatus")
    CommonResult updateOrderStatus(@RequestParam("code") String code,
                                   @RequestParam("orderStatus") String orderStatus);

    /**
     * 根据交易号所有子订单skuId和数量
     * @param code 交易号 或 订单号
     * @param orderType 支付订单类型
     * @return
     */
    @GetMapping("getSkuIdAndCount")
    CommonResult getSkuIdAndCount(@RequestParam("code") String code,
                                  @RequestParam("orderType") String orderType);

    /**
     * 根据参数获取订单信息
     * @param params 参数
     * @return
     */
    @GetMapping("getOrderByParams")
    CommonResult getOrderByParams(@RequestParam Map<String, Object> params);
}
