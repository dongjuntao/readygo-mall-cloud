package com.mall.search.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2023/3/10 16:09
 * @Version 1.0
 */
@Data
public class ESGoodsSku {

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
     * 原价（元）
     */
    private BigDecimal originalPrice;
    /**
     * 销售价（元）
     */
    private BigDecimal sellingPrice;
    /**
     * 库存
     */
    private Integer stock;
    /**
     * 是否启用
     */
    private Boolean enable;
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
    /**
     * 商品名称（不入库）
     */
    @TableField(exist = false)
    private String name;

    /**
     * 参与秒杀时的信息，包括【秒杀价，秒杀库存】
     */
    @TableField(exist = false)
    private ESSeckillInfo seckillInfo;
}
