package com.mall.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author DongJunTao
 * @Description 订单-发票信息
 * @Date 2022/6/15 22:09
 * @Version 1.0
 */
@Data
@TableName("order_invoice")
public class OrderInvoiceEntity {

    @TableId(type = IdType.AUTO)
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
