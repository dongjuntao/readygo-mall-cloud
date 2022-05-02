package com.mall.member.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author DongJunTao
 * @Description 商品收藏信息
 * @Date 2022/5/2 16:00
 * @Version 1.0
 */
@Data
public class CollectGoodsVO {
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
