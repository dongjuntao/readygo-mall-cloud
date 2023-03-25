package com.mall.search.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.apache.ibatis.javassist.bytecode.analysis.Analyzer;
import org.elasticsearch.index.analysis.Analysis;
import org.elasticsearch.index.analysis.AnalysisMode;
import org.elasticsearch.index.analysis.IndexAnalyzers;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2023/3/10 16:08
 * @Version 1.0
 */
@Data
@Document(indexName = "ready")
public class ESGoods implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;
    /**
     * 商品名称
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String name;
    /**
     * 关键字
     */
    @Field(type = FieldType.Keyword, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String keyword;
    /**
     * 商户id
     */
    @Field(type = FieldType.Long, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private Long adminUserId;
    /**
     * 商品一级分类id
     */
    @Field(type = FieldType.Long, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private Long goodsCategoryIdFirst;
    /**
     * 商品二级分类id
     */
    @Field(type = FieldType.Long, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private Long goodsCategoryIdSecond;
    /**
     * 商品三级分类id
     */
    @Field(type = FieldType.Long, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private Long goodsCategoryIdThird;
    /**
     * 是否在售
     */
    @Field(type = FieldType.Boolean, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private Boolean onSale;
    /**
     * 运费设置
     */
    @Field(type = FieldType.Integer, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private Integer freightSetting;
    /**
     * 商品编码
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String code;
    /**
     * 所属品牌id
     */
    @Field(type = FieldType.Long, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private Long brandId;
    /**
     * 商品单位
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String unit;
    /**
     * 商品简介
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word",searchAnalyzer = "ik_smart")
    private String description;
    /**
     * 商品图片
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word",searchAnalyzer = "ik_smart")
    private String images;
    /**
     * 商品详情
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word",searchAnalyzer = "ik_smart")
    private String infoDetail;
    /**
     * 商品规格类型（0：单规格， 1：多规格）
     */
    @Field(type = FieldType.Integer, analyzer = "ik_max_word",searchAnalyzer = "ik_smart")
    private Integer specificationType;
    /**
     * 商品参数
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word",searchAnalyzer = "ik_smart")
    private String params;
    /**
     * 商品赠送积分
     */
    @Field(type = FieldType.Integer, analyzer = "ik_max_word",searchAnalyzer = "ik_smart")
    private Integer points;
    /**
     * 商品推荐
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word",searchAnalyzer = "ik_smart")
    private String recommend;
    /**
     * 物流重量
     */
    @Field(type = FieldType.Auto, analyzer = "ik_max_word",searchAnalyzer = "ik_smart")
    private BigDecimal weight;
    /**
     * 物流体积
     */
    @Field(type = FieldType.Auto, analyzer = "ik_max_word",searchAnalyzer = "ik_smart")
    private BigDecimal volume;
    /**
     * 创建时间
     */
    @Field(type = FieldType.Date, analyzer = "ik_max_word",searchAnalyzer = "ik_smart")
    private Date createTime;
    /**
     * 更新时间
     */
    @Field(type = FieldType.Date, analyzer = "ik_max_word",searchAnalyzer = "ik_smart")
    private Date updateTime;
    /**
     * sku列表
     */
    @Field(type = FieldType.Nested, analyzer = "ik_max_word",searchAnalyzer = "ik_smart")
    private List<ESGoodsSku> goodsSkuList;
    /**
     * 所属商户名称
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word",searchAnalyzer = "ik_smart")
    private String merchantName;
    /**
     * 所属品牌名称
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word",searchAnalyzer = "ik_smart")
    private String brandName;
}
