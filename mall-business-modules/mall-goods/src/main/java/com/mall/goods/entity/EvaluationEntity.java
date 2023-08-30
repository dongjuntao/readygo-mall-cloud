package com.mall.goods.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author DongJunTao
 * @Description 商品评价表
 * @Date 2023/8/17 16:15
 * @Version 1.0
 */
@Data
@TableName("evaluation")
public class EvaluationEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;
    /**
     * 评价人id
     */
    private Long memberId;
    /**
     * 商品id
     */
    private Long goodsId;
    /**
     * 商品skuId
     */
    private Long skuId;
    /**
     * 所属商户id
     */
    private Long merchantId;
    /**
     * 物流评价等级【1,2,3,4,5】(星)
     */
    private Integer logisticsEvaluationLevel;
    /**
     * 服务评价等级【1,2,3,4,5】(星)
     */
    private Integer serviceEvaluationLevel;
    /**
     * 描述评价等级【1,2,3,4,5】(星)
     */
    private Integer descriptionEvaluationLevel;
    /**
     * 商品评价等级【1(差评),2(中评),3(好评)）
     */
    private Integer goodsEvaluationLevel;
    /**
     * 评价内容
     */
    private String evaluateContent;
    /**
     * 回复内容
     */
    private String answerContent;
    /**
     * 评价图片(多个图片以逗号隔开)
     */
    private String images;
    /**
     * 订单号
     */
    private String orderCode;
    /**
     * 子订单号
     */
    private String subOrderCode;
    /**
     * 评价时间
     */
    private Date evaluateTime;
    /**
     * 回复时间（商家）
     */
    private Date answerTime;

}
