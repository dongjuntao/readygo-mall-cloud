package com.mall.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.mall.order.enums.AfterSalesStatusEnum;
import com.mall.order.enums.CommentStatusEnum;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author DongJunTao
 * @Description 订单详细表
 * @Date 2022/6/15 22:16
 * @Version 1.0
 */
@Data
@TableName("order_detail")
public class OrderDetailEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long orderId;

    /**
     * 父订单号
     */
    private String orderCode;

    /**
     * 子订单号
     */
    private String subCode;

    /**
     * 商品id
     */
    private Long goodsId;

    /**
     * 商品skuId
     */
    private Long goodsSkuId;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 商品图片
     */
    private String goodsImage;
    /**
     * 商品销售价格
     */
    @JsonSerialize(using= ToStringSerializer.class)
    private BigDecimal goodsSellingPrice;
    /**
     * 购买数量
     */
    private Integer goodsCount;
    /**
     * 小计
     */
    private BigDecimal goodsSubTotal;

    /**
     * 售后状态
     * @see com.mall.order.enums.AfterSalesStatusEnum
     */
    private AfterSalesStatusEnum afterSalesStatus;

    /**
     * 评价状态
     * @see com.mall.order.enums.CommentStatusEnum
     */
    private CommentStatusEnum commentStatus;

}
