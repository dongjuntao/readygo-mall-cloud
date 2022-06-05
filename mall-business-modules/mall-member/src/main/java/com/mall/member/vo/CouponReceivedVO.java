package com.mall.member.vo;

import lombok.Data;

import java.util.Date;

/**
 * @Author DongJunTao
 * @Description 已领取的优惠券VO
 * @Date 2022/6/5 9:57
 * @Version 1.0
 */
@Data
public class CouponReceivedVO {

    /**
     * 主键
     */
    private Long id;
    /**
     * 会员id
     */
    private Long memberId;
    /**
     * 优惠券id
     */
    private Long couponId;
    /**
     * 优惠券有效期（开始）
     */
    private Date validPeriodStart;
    /**
     * 优惠券有效期（结束）
     */
    private Date validPeriodEnd;
    /**
     * 优惠券是否使用
     */
    private Boolean used;
    /**
     * 领取时间
     */
    private Date createTime;

    /**
     * 优惠券名称
     */
    private String name;
    /**
     * 优惠券类型（0：满减券；1：满折券）
     */
    private Integer type;
    /**
     * 优惠券来源（优惠券来源（0：平台；1：商家））
     */
    private Integer source;
    /**
     * 有门槛时最低消费
     */
    private Double minConsumption;
    /**
     * 优惠额度（如果是满减券，该字段是减钱数，如果是满折券，该字段是打折数）
     */
    private Double discountAmount;
    /**
     * 使用范围（0：全部商品；1：指定分类， 2：指定商品）
     */
    private Integer useScope;
    /**
     * 商家名称
     */
    private String merchantName;
}
