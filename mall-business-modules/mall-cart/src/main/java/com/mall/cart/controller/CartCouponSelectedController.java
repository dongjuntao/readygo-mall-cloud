package com.mall.cart.controller;

import com.mall.cart.entity.CartCouponSelectedEntity;
import com.mall.cart.entity.CartInvoiceEntity;
import com.mall.cart.service.CartCouponSelectedService;
import com.mall.common.base.CommonResult;
import com.mall.common.base.utils.CurrentUserContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author DongJunTao
 * @Description 购物车中已选中的优惠券Controller
 * @Date 2022/6/5 15:35
 * @Version 1.0
 */
@RestController
@RequestMapping( "cartCouponSelected")
public class CartCouponSelectedController {

    @Autowired
    private CartCouponSelectedService cartCouponSelectedService;

    /**
     * 选择优惠券
     * @param use 使用还是不使用
     * @param cartCouponSelected 选中信息
     * @return
     */
    @PostMapping("select")
    public CommonResult select(@RequestParam("use") Boolean use,
                             @RequestBody CartCouponSelectedEntity cartCouponSelected) {
        Long memberId = CurrentUserContextUtil.getCurrentUserInfo().getUserId();
        cartCouponSelected.setMemberId(memberId);
        return cartCouponSelectedService.select(cartCouponSelected, use)>0 ? CommonResult.success(cartCouponSelected) : CommonResult.fail();
    }

    /**
     * 获取用户已选中的优惠券
     * @return
     */
    @GetMapping("getSelected")
    public CommonResult getSelected() {
        List<CartCouponSelectedEntity> cartCouponSelectedList
                = cartCouponSelectedService.getSelected(CurrentUserContextUtil.getCurrentUserInfo().getUserId());
        return CommonResult.success(cartCouponSelectedList);
    }
}
