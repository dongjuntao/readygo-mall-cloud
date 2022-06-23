package com.mall.order.controller;

import com.mall.common.base.CommonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author DongJunTao
 * @Description 支付相关
 * @Date 2022/6/18 16:28
 * @Version 1.0
 */
@RestController
@RequestMapping( "pay")
public class PayController {

    @GetMapping("payInfo")
    public CommonResult getPayInfoFromOrder() {
        return null;
    }
}
