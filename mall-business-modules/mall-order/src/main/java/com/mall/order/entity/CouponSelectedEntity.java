package com.mall.order.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author DongJunTao
 * @Description 购物车中已选中的优惠券
 * @Date 2022/6/5 15:23
 * @Version 1.0
 */
@Data
@TableName("coupon_selected")
public class CouponSelectedEntity {

    @TableId
    private Long id;
    /**
     * 会员id
     */
    private Long memberId;
    /**
     * 已领取优惠券id（mall-member服务中的receivedCoupon）
     */
    private Long receivedCouponId;

    /**
     * 是否已逻辑删除
     */
    private Boolean isDel;
}
