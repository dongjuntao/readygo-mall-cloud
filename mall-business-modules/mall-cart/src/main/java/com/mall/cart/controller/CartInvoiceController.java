package com.mall.cart.controller;

import com.mall.cart.entity.CartInvoiceEntity;
import com.mall.cart.service.CartInvoiceService;
import com.mall.common.base.CommonResult;
import com.mall.common.base.utils.CurrentUserContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 购物车-发票
 * @Date 2022/5/29 21:57
 * @Version 1.0
 */
@RestController
@RequestMapping( "cartInvoice")
public class CartInvoiceController {

    @Autowired
    private CartInvoiceService cartInvoiceService;

    /**
     * 添加发票
     * @param cartInvoice 发票信息
     * @return
     */
    @PostMapping("saveOrUpdate")
    public CommonResult save(@RequestBody CartInvoiceEntity cartInvoice) {
        Long memberId = CurrentUserContextUtil.getCurrentUserInfo().getUserId();
        cartInvoice.setMemberId(memberId);
        return cartInvoiceService.saveCartInvoice(cartInvoice)>0 ? CommonResult.success() : CommonResult.fail();
    }

    /**
     * 根据参数查询发票
     * @param params 发票信息id
     * @return
     */
    @GetMapping("getCartInvoiceByParams")
    public CommonResult getCartInvoiceByParams(@RequestParam Map<String, Object> params) {
        params.put("memberId",CurrentUserContextUtil.getCurrentUserInfo().getUserId());
        CartInvoiceEntity cartInvoice = cartInvoiceService.getCartInvoiceByParams(params);
        return CommonResult.success(cartInvoice);
    }

    /**
     * 根据id删除发票
     * @param cartInvoiceId 发票信息id
     * @return
     */
    @DeleteMapping("deleteCartInvoiceById")
    public CommonResult deleteCartBillById(@RequestParam("cartInvoiceId") Long cartInvoiceId) {
        return cartInvoiceService.deleteCartInvoice(cartInvoiceId)>0 ? CommonResult.success() : CommonResult.fail();
    }
}
