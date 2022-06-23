package com.mall.order.vo;

import lombok.Data;

/**
 * @Author DongJunTao
 * @Description 发票数据
 * @Date 2022/6/17 11:23
 * @Version 1.0
 */
@Data
public class OrderInvoiceVO {

    private Long id;

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 发票类型 0:电子普通发票 1:增值税发票
     */
    private Integer invoiceType;
    /**
     * 发票抬头类型 0：个人 1：单位
     */
    private Integer invoiceTitleType;
    /**
     * 个人名称或单位名称
     */
    private String invoiceTitle;
    /**
     * 纳税人识别号
     */
    private String taxNumber;
    /**
     * 发票内容 0:商品明细 1:商品类别
     */
    private Integer invoiceContent;
    /**
     * 收票人手机
     */
    private String mobile;
    /**
     * 收票人邮箱
     */
    private String email;
}
