package com.mall.cart.entity;

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
@TableName("cart_coupon_selected")
public class CartCouponSelectedEntity {

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
}
