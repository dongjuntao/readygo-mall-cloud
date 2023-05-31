package com.mall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.order.entity.InvoiceEntity;

import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 购物车-发票信息
 * @Date 2022/5/29 21:46
 * @Version 1.0
 */
public interface InvoiceService extends IService<InvoiceEntity> {

    int saveInvoice(InvoiceEntity cartBill);

    int deleteInvoice(Long cartBillId);

    InvoiceEntity getInvoiceByParams(Long id, Long memberId);
}
