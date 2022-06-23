package com.mall.order.vo;

import com.mall.order.entity.OrderEntity;
import com.mall.order.enums.OrderStatusEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author DongJunTao
 * @Description 订单基本数据
 * @Date 2022/6/17 11:21
 * @Version 1.0
 */
@Data
public class OrderVO {

    private Long id;

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
    private Long merchantName;

    /**
     * 订单号
     */
    private String code;

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
}
