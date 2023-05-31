package com.mall.order.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mall.admin.api.feign.front.FeignFrontFreightTemplateService;
import com.mall.cart.api.feign.FeignCartService;
import com.mall.common.base.CommonResult;
import com.mall.common.base.enums.ResultCodeEnum;
import com.mall.common.base.utils.CurrentUserContextUtil;
import com.mall.coupon.api.feign.front.FeignFrontCouponService;
import com.mall.goods.api.FeignFrontGoodsService;
import com.mall.member.api.FeignCouponReceivedService;
import com.mall.order.entity.CouponSelectedEntity;
import com.mall.order.enums.CartTypeEnum;
import com.mall.order.service.CouponSelectedService;
import com.mall.order.service.PayInfoGoodsService;
import com.mall.order.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

/**
 * @Author DongJunTao
 * @Description 订单结算页 商家及商品信息
 * @Date 2022/5/8 16:00
 * @Version 1.0
 */
@RestController
@RequestMapping( "payInfo")
public class PayInfoGoodsController {

    @Autowired
    private PayInfoGoodsService payInfoGoodsService;

    /**
     * 获取结算页信息【商品信息】
     * @return
     */
    @GetMapping("goods")
    public CommonResult goods(@RequestParam Map<String, Object> params) {
        PayVO payVO = payInfoGoodsService.getPayInfoGoods(params);
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), payVO);
    }

}
