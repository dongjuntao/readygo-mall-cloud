package com.mall.member.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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
     * 优惠券是否使用（0：未使用 1：已使用 2:已过期 3：冻结中【表示已选中该优惠券，提交了订单，还未支付】）
     */
    private Integer useStatus;
    /**
     * 有效期开始
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date validPeriodStart;
    /**
     * 有效期结束
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date validPeriodEnd;
    /**
     * 领取时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 会员名称
     */
    @TableField(exist = false)
    private String memberName;

}
