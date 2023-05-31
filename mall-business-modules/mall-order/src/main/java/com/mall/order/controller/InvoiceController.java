package com.mall.order.controller;

import com.mall.common.base.CommonResult;
import com.mall.common.base.utils.CurrentUserContextUtil;
import com.mall.order.entity.InvoiceEntity;
import com.mall.order.service.InvoiceService;
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
@RequestMapping( "invoice")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    /**
     * 添加发票
     * @param invoice 发票信息
     * @return
     */
    @PostMapping("saveOrUpdate")
    public CommonResult save(@RequestBody InvoiceEntity invoice) {
        Long memberId = CurrentUserContextUtil.getCurrentUserInfo().getUserId();
        invoice.setMemberId(memberId);
        return invoiceService.saveInvoice(invoice)>0 ? CommonResult.success() : CommonResult.fail();
    }

    /**
     * 根据参数查询发票
     * @return
     */
    @GetMapping("getInvoiceByParams")
    public CommonResult getCartInvoiceByParams(@RequestParam(value = "id",required = false) Long id) {
        Long memberId = CurrentUserContextUtil.getCurrentUserInfo().getUserId();
        InvoiceEntity cartInvoice = invoiceService.getInvoiceByParams(id, memberId);
        return CommonResult.success(cartInvoice);
    }

    /**
     * 根据id删除发票
     * @param cartInvoiceId 发票信息id
     * @return
     */
    @DeleteMapping("deleteInvoiceById")
    public CommonResult deleteCartBillById(@RequestParam("cartInvoiceId") Long cartInvoiceId) {
        return invoiceService.deleteInvoice(cartInvoiceId)>0 ? CommonResult.success() : CommonResult.fail();
    }
}
