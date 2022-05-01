package com.mall.member.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2022/4/29 20:59
 * @Version 1.0
 */
@Data
public class FootprintGoodsVO {
    /**
     * 商品id
     */
    private Long id;
    /**
     * skuId
     */
    private Long skuId;
    /**
     * 商品名称
     */
    private String name;
    /**
     * 商品图片
     */
    private String image;
    /**
     * 商品价格
     */
    private BigDecimal price;

}
