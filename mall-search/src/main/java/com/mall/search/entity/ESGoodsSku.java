package com.mall.search.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2023/3/10 16:09
 * @Version 1.0
 */
@Data
public class ESGoodsSku {

    @Field(type = FieldType.Long)
    private Long id;
    /**
     * 商品id
     */
    @Field(type = FieldType.Long)
    private Long goodsId;
    /**
     * 商品sku编码
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String code;
    /**
     * 原价（元）
     */
    @Field(type = FieldType.Auto)
    private BigDecimal originalPrice;
    /**
     * 销售价（元）
     */
    @Field(type = FieldType.Auto)
    private BigDecimal sellingPrice;
    /**
     * 库存
     */
    @Field(type = FieldType.Integer)
    private Integer stock;
    /**
     * 是否启用
     */
    @Field(type = FieldType.Boolean)
    private Boolean enable;
    /**
     * 商品sku图片
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word",searchAnalyzer = "ik_smart")
    private String image;
    /**
     * sku单品销量
     */
    @Field(type = FieldType.Integer)
    private Integer sales;
    /**
     * 商品sku扩展属性
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word",searchAnalyzer = "ik_smart")
    private String extendAttr;
    /**
     * 商品sku扩展属性值
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word",searchAnalyzer = "ik_smart")
    private String extendValue;
    /**
     * 商品名称
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word",searchAnalyzer = "ik_smart")
    private String name;
    /**
     * 参与秒杀时的信息，包括【秒杀价，秒杀库存】
     */
    @Field(type = FieldType.Nested)
    private ESSeckillInfo seckillInfo;
}
