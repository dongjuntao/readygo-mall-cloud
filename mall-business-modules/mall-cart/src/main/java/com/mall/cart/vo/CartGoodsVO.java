package com.mall.cart.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2022/5/14 16:35
 * @Version 1.0
 */
@Data
public class CartGoodsVO {

    private Long id;
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
     * 库存量（用于判断是否有货）
     */
    private Integer stock;
    /**
     * 是否被选中
     */
    private Boolean checked;
    /**
     * 小计
     */
    private BigDecimal subTotal;

}
