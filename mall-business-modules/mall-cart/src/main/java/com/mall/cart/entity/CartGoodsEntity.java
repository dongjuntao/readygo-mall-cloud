package com.mall.cart.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Author DongJunTao
 * @Description 购物车商品信息
 * @Date 2022/5/8 15:27
 * @Version 1.0
 */
@Data
@TableName("cart_goods")
public class CartGoodsEntity {

    @TableId
    private Long id;
    /**
     * 购物车id
     */
    private Long cartId;
    /**
     * 商品id
     */
    private Long goodsId;
    /**
     * 商品skuId
     */
    private Long goodsSkuId;
    /**
     * 购买的数量
     */
    private Integer count;
    /**
     * 是否被选中
     */
    private Boolean checked;
    /**
     * 加入时间
     */
    private Date createTime;

}
