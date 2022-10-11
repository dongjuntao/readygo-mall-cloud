package com.mall.goods.constants;

/**
 * @Author DongJunTao
 * @Description redis key相关的常量
 * @Date 2021/7/5 13:27
 * @Version 1.0
 */
public class RedisKeyConstant {
    /**商品分类key*/
    public static final String GOODS_CATEGORY_KEY = "goods:category";
    /**商品分类HashKey*/
    public static final String GOODS_CATEGORY_HASH_KEY = "goodsCategory";

    /**商品分类key（合并后的）*/
    public static final String GOODS_CATEGORY_MERGED_KEY = "goods:category:merged";
    /**商品分类HashKey（合并后的）*/
    public static final String GOODS_CATEGORY_MERGED_HASH_KEY = "goodsCategory:merged";
}
