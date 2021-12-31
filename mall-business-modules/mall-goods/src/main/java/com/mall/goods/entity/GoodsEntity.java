package com.mall.goods.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
    private Long adminUserId;
    /**
     * 商品分类id集（1,2,3）
     */
    private String goodsCategoryIds;
    /**
     * 是否在售
     */
    private Boolean onSale;
    /**
     * 运费设置
     */
    private Integer freightSetting;
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
    private String infoDetail;
    /**
     * 商品规格类型（0：单规格， 1：多规格）
     */
    private Integer specificationType;
    /**
     * 商品参数
     */
    private String params;
    /**
     * 商品赠送积分
     */
    private Integer points;
    /**
     * 商品推荐
     */
    private String recommend;
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
    /**
     * 所属商户名称
     */
    @TableField(exist = false)
    private String merchantName;
    /**
     * 所属品牌名称
     */
    @TableField(exist = false)
    private String brandName;
}
