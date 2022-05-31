package com.mall.cart.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author DongJunTao
 * @Description 结算页商品信息
 * @Date 2022/5/28 15:58
 * @Version 1.0
 */
@Data
public class PayGoodsVO {
    private Long id;

    /**
     * 商品id
     */
    private Long goodsId;

    /**
     * 商品skuId
     */
    private Long goodsSkuId;
    /**
     * 商品名称
     */
    private String name;
    /**
     * 商品图片
     */
    private String image;
    /**
     * 销售价格
     */
    private BigDecimal sellingPrice;
    /**
     * 购买数量
     */
    private Integer count;
    /**
     * 小计
     */
    private BigDecimal subTotal;
}
