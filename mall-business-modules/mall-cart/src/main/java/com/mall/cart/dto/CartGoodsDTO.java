package com.mall.cart.dto;

import lombok.Data;

/**
 * @Author DongJunTao
 * @Description 购物车商品信息
 * @Date 2022/5/8 16:09
 * @Version 1.0
 */
@Data
public class CartGoodsDTO {

    /**
     * 商品id
     */
    private Long goodsId;
    /**
     * 商品skuId
     */
    private Long goodsSkuId;
    /**
     * 购买数量
     */
    private Integer count;

}
