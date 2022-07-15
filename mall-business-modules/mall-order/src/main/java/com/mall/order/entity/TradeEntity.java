package com.mall.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.mall.order.enums.TradeStatusEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author DongJunTao
 * @Description 交易表（交易中包含订单，订单集合了某一商家的商品，订单里每个商品为子订单）
 * @Date 2022/6/18 21:39
 * @Version 1.0
 */
@Data
@TableName("trade")
public class TradeEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 交易号
     */
    private String code;

    /**
     * 会员id（买家id）
     */
    private Long memberId;

    /**
     * 会员名称（买家名称）
     */
    private String memberName;

    /**
     * 付款状态
     * @see TradeStatusEnum
     */
    private TradeStatusEnum payStatus;

    /**
     * 交易时间
     */
    private Date tradeTime;

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
     * 交易里包含的订单信息
     */
    @TableField(exist = false)
    private List<OrderEntity> orderList;

}
