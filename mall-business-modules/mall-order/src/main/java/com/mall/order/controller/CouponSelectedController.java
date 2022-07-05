package com.mall.order.controller;

import com.mall.common.base.CommonResult;
import com.mall.common.base.utils.CurrentUserContextUtil;
import com.mall.order.entity.CouponSelectedEntity;
import com.mall.order.service.CouponSelectedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.SaslServer;
import java.util.List;

/**
 * @Author DongJunTao
 * @Description 订单结算页 已选的优惠券
 * @Date 2022/6/5 15:35
 * @Version 1.0
 */
@RestController
@RequestMapping( "couponSelected")
public class CouponSelectedController {

    @Autowired
    private CouponSelectedService couponSelectedService;

    /**
     * 选择优惠券
     * @param use 使用还是不使用
     * @param couponSelected 选中信息
     * @return
     */
    @PostMapping("select")
    public CommonResult select(@RequestParam("use") Boolean use,
                             @RequestBody CouponSelectedEntity couponSelected) {
        Long memberId = CurrentUserContextUtil.getCurrentUserInfo().getUserId();
        couponSelected.setMemberId(memberId);
        return couponSelectedService.select(couponSelected, use)>0 ? CommonResult.success(couponSelected) : CommonResult.fail();
    }

    /**
     * 获取用户已选中的优惠券
     * @return
     */
    @GetMapping("getSelected")
    public CommonResult getSelected() {
        System.out.println("CurrentUserContextUtil.getCurrentUserInfo() == "+CurrentUserContextUtil.getCurrentUserInfo());
        CouponSelectedEntity cartCouponSelected
                = couponSelectedService.getSelected(CurrentUserContextUtil.getCurrentUserInfo().getUserId());
        return CommonResult.success(cartCouponSelected);
    }
}
