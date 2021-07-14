package com.mall.goods.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author DongJunTao
 * @Description 商品sku表
 * @Date 2021/6/22 11:02
 * @Version 1.0
 */
@Data
@TableName("goods_sku")
public class GoodsSkuEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;
    /**
     * 商品id
     */
    private Long goodsId;
    /**
     * 商品sku编码
     */
    private String code;
    /**
     * 商品sku名称
     */
    private String name;
    /**
     * 价格
     */
    private BigDecimal price;
    /**
     * 库存
     */
    private Integer stock;
    /**
     * 重量
     */
    private double weight;
    /**
     * 体积
     */
    private double volume;
    /**
     * 是否启用
     */
    private boolean enable;
    /**
     * 商品sku图片
     */
    private String image;
    /**
     * 商品sku扩展属性
     */
    private String extendAttr;
    /**
     * 商品sku扩展属性值
     */
    private String extendValue;
}