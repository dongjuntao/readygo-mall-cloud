package com.mall.goods.vo;

import lombok.Data;

/**
 * @Author DongJunTao
 * @Description 根据商品分类统计商品数量
 * @Date 2023/7/10 17:11
 * @Version 1.0
 */
@Data
public class GoodsCountByCategoryVO {

    private String categoryName;//商品分类

    private Integer count; //数量
}
