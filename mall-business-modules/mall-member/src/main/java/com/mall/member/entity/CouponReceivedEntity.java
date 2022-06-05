package com.mall.member.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Author DongJunTao
 * @Description 我的优惠券
 * @Date 2022/6/4 15:13
 * @Version 1.0
 */
@Data
@TableName("coupon_received")
public class CouponReceivedEntity {
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
     * 优惠券是否使用
     */
    private Integer useStatus;
    /**
     * 领取时间
     */
    private Date createTime;

}
