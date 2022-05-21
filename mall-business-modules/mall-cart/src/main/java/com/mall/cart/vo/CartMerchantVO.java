package com.mall.cart.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author DongJunTao
 * @Description 购物车每个商家的商品列表信息
 * @Date 2022/5/14 16:31
 * @Version 1.0
 */
@Data
public class CartMerchantVO {

    /**
     * 商家id
     */
    private Long merchantId;

    /**
     * 商家名称
     */
    private String merchantName;

    /**
     * 商品信息
     */
    private List<CartGoodsVO> cartGoodsList;


}
