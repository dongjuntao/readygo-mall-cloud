package com.mall.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mall.order.enums.OrderStatusEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author DongJunTao
 * @Description 订单表
 * @Date 2022/6/15 20:59
 * @Version 1.0
 */
@Data
@TableName("order_info")
public class OrderEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 订单号
     */
    private String code;

    /**
     * 交易id
     */
    private Long tradeId;

    /**
     * 会员id（买家id）
     */
    private Long memberId;

    /**
     * 会员名称（买家名称）
     */
    private String memberName;

    /**
     * 商家id（卖家id）
     */
    private Long merchantId;

    /**
     * 商家名称（卖家名称）
     */
    private String merchantName;

    /**
     * 订单来源（0：PC 1:APP 2:小程序）
     */
    private Integer source;

    /**
     * 订单状态
     */
    private OrderStatusEnum status;

    /**
     * 支付方式（0：微信 1：支付宝）
     */
    private Integer payType;

    /**
     * 支付时间
     */
    private Date payTime;

    /**
     * 订单备注
     */
    private String remark;

    /**
     * 商品总金额
     */
    private BigDecimal totalPrice;

    /**
     * 运费
     */
    private BigDecimal freight;

    /**
     * 最终金额（应付金额）
     */
    private BigDecimal finalPrice;

    /**
     * 下单时间
     */
    private Date createTime;

    /**
     * 订单更新时间
     */
    private Date updateTime;
}
