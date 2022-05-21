package com.mall.goods.vo;

import lombok.Data;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2022/4/10 21:06
 * @Version 1.0
 */
@Data
public class GoodsCategoryInfoVO {
    /**
     * 商品分类名称（多个，以分隔符分开）
     */
    private String categoryName;
    /**
     * 商品分类id（多个，以分隔符分开）
     */
    private Long categoryId;
}
