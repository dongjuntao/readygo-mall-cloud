package com.mall.order.vo;

import lombok.Data;

/**
 * @Author DongJunTao
 * @Description 接收页面传来的订单相关信息
 * @Date 2022/6/17 11:24
 * @Version 1.0
 */
@Data
public class TradeDetailVO {

    /**
     * 商家及商品相关信息
     */
    private PayVO payVO;

    /**
     * 发票信息
     */
    private OrderInvoiceVO orderInvoiceVO;

    /**
     * 收货人信息
     */
    private RecipientInfoVO recipientInfoVO;
}
