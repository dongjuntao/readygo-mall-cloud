package com.mall.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.mall.order.enums.OrderStatusEnum;
import com.mall.order.enums.PayTypeEnum;
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
     * 交易号
     */
    private String tradeCode;

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
     * 取消订单原因
     */
    private String cancelReason;

    /**
     * 支付方式（0：微信 1：支付宝）
     */
    private PayTypeEnum payType;

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
    @JsonSerialize(using= ToStringSerializer.class)
    private BigDecimal totalPrice;

    /**
     * 运费
     */
    @JsonSerialize(using= ToStringSerializer.class)
    private BigDecimal freight;

    /**
     * 最终金额（应付金额）
     */
    @JsonSerialize(using= ToStringSerializer.class)
    private BigDecimal finalPrice;

    /**
     * 收件人姓名
     */
    private String recipientName;

    /**
     * 收件人详细地址
     */
    private String recipientDetailAddress;

    /**
     * 收件人手机号码
     */
    private String recipientMobile;

    /**
     * 收货地区名称（省、市、区县）如（安徽省 淮南市 寿县）
     */
    private String regionNames;

    /**
     * 下单时间
     */
    private Date createTime;

    /**
     * 订单更新时间
     */
    private Date updateTime;

    /**
     * 包含的订单明细
     */
    @TableField(exist = false)
    private List<OrderDetailEntity> orderDetailList;
}
