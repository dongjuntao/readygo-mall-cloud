package com.mall.order.controller;

import com.mall.common.base.CommonResult;
import com.mall.order.service.OrderDetailService;
import com.mall.order.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author DongJunTao
 * @Description 订单controller
 * @Date 2022/6/30 21:28
 * @Version 1.0
 */
@RestController
@RequestMapping( "order")
public class OrderController {

    @Autowired
    private TradeService tradeService;

    @Autowired
    private OrderDetailService orderDetailService;

    /**
     * 支付成功后修改状态
     * @param code 交易号
     * @return
     */
    @PostMapping("updateStatus")
    public CommonResult updateStatus(@RequestParam("code") String code) {
        return tradeService.updateTradeStatus(code) > 0 ? CommonResult.success() : CommonResult.fail();
    }

    /**
     * 根据交易号所有子订单skuId和数量
     * @param code 交易号
     * @return
     */
    @GetMapping("getSkuIdAndCount")
    public CommonResult getSkuIdAndCount(@RequestParam("code") String code) {
        return CommonResult.success(orderDetailService.getSkuIdAndCount(code));
    }
}
