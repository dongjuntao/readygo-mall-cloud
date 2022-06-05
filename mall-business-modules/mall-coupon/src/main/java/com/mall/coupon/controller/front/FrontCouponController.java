package com.mall.coupon.controller.front;

import com.mall.common.base.CommonResult;
import com.mall.common.base.enums.ResultCodeEnum;
import com.mall.common.base.utils.PageUtil;
import com.mall.coupon.entity.CouponEntity;
import com.mall.coupon.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 优惠券controller 供们门户端使用
 * @Date 2021/10/31 22:08
 * @Version 1.0
 */
@RestController
@RequestMapping("front/coupon")
public class FrontCouponController {

    @Autowired
    private CouponService couponService;

    /**
     * 所有优惠券列表
     */
    @GetMapping("/list")
    public CommonResult list(@RequestParam Long[] ids){
        List<CouponEntity> couponList = couponService.getBatchByIds(ids);
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), couponList);
    }

    /**
     * 查询优惠券详细信息
     */
    @GetMapping("/getById")
    public CommonResult getById(@RequestParam("couponId") Long couponId){
        CouponEntity coupon = couponService.getById(couponId);
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), coupon);
    }

}
