package com.mall.goods.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mall.goods.enums.FreightEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author DongJunTao
 * @Description 商品表
 * @Date 2021/6/21 17:04
 * @Version 1.0
 */
@Data
@TableName("goods")
public class GoodsEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;
    /**
     * 商品名称
     */
    private String name;
    /**
     * 关键字
     */
    private String keyword;
    /**
     * 商户id
     */
    private Long merchantId;
    /**
     * 商品分类id
     */
    private Long goodsCategoryId;
    /**
     * 是否在售
     */
    private Boolean onSale;
    /**
     * 运费设置
     */
    private FreightEnum freightSetting;
    /**
     * 商品编码
     */
    private String code;
    /**
     * 所属品牌id
     */
    private Long brandId;
    /**
     * 商品单位
     */
    private String unit;
    /**
     * 商品简介
     */
    private String description;
    /**
     * 商品图片
     */
    private String images;
    /**
     * 商品详情
     */
    private byte[] infoDetail;
    /**
     * 商品参数
     */
    private String params;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * sku列表
     */
    @TableField(exist = false)
    private List<GoodsSkuEntity> goodsSkuList;
}
