package com.mall.member.controller.back;

import com.mall.common.base.CommonResult;
import com.mall.common.base.enums.ResultCodeEnum;
import com.mall.common.base.utils.PageUtil;
import com.mall.member.service.CouponReceivedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2023/3/30 13:13
 * @Version 1.0
 */
@RestController
@RequestMapping("back/coupon/received")
public class BackCouponReceivedController {

    @Autowired
    private CouponReceivedService couponReceivedService;

    /**
     * 分页查询优惠券领取列表
     * @return
     */
    @GetMapping("getCouponReceivedList")
    public CommonResult getCouponReceivedList(@RequestParam(value = "pageNum", required = false) Integer pageNum,
                                              @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                              @RequestParam(value = "couponId", required = false) Long couponId) {
        PageUtil couponReceivedList = couponReceivedService.queryPageWithMemberInfo(pageNum, pageSize, couponId);
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), couponReceivedList);
    }
}
