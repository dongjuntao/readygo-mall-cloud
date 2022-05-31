package com.mall.cart.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.cart.entity.CartInvoiceEntity;

import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 购物车-发票信息
 * @Date 2022/5/29 21:46
 * @Version 1.0
 */
public interface CartInvoiceService extends IService<CartInvoiceEntity> {

    int saveCartInvoice(CartInvoiceEntity cartBill);

    int deleteCartInvoice(Long cartBillId);

    CartInvoiceEntity getCartInvoiceByParams(Map<String, Object> params);
}
