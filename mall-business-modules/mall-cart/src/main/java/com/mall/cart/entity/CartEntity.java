package com.mall.cart.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

/**
 * @Author DongJunTao
 * @Description 购物车
 * @Date 2022/5/8 15:25
 * @Version 1.0
 */
@Data
@TableName("cart")
public class CartEntity {

    @TableId
    private Long id;
    /**
     * 会员id
     */
    private Long memberId;
    /**
     * 商户id
     */
    private Long merchantId;

    /**
     * 关联的商品信息
     */
    @TableField(exist = false)
    private List<CartGoodsEntity> cartGoodsList;
}
