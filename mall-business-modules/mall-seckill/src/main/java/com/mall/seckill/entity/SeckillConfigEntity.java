package com.mall.seckill.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mall.seckill.vo.GoodsSkuVO;
import lombok.Data;

import java.sql.Time;
import java.util.Date;
import java.util.List;

/**
 * @Author DongJunTao
 * @Description 秒杀配置
 * @Date 2022/1/5 22:01
 * @Version 1.0
 */
@Data
@TableName("seckill_config")
public class SeckillConfigEntity {

    @TableId
    private Long id;
    /**
     * 商品id
     */
    private Long goodsId;
    /**
     * 所属商户id
     */
    private Long adminUserId;
    /**
     * 秒杀开始日期（某天）
     */
    private Date seckillStartDate;
    /**
     * 秒杀结束日期（某天）
     */
    private Date seckillEndDate;
    /**
     * 秒杀开始时间点
     */
    private Time seckillStartTime;
    /**
     * 秒杀结束时间点
     */
    private Time seckillEndTime;
    /**
     * 每人限购次数
     */
    private Integer perLimit;
    /**
     * 状态（0：禁用；1：启用）
     */
    private Boolean status;
    /**
     * 审核状态（0：待审核，1：审核通过，2：审核拒绝）
     */
    private Integer authStatus;
    /**
     * 审核意见
     */
    private String authOpinion;
    /**
     * 创建人id
     */
    private Long createBy;
    /**
     * 修改人id
     */
    private Long updateBy;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 关联的商品详细信息【包括goods_sku和seckill_goods_sku的信息】
     */
    @TableField(exist = false)
    private List<GoodsSkuVO> goodsSkuList;
    /**
     * 所属商户名称
     */
    @TableField(exist = false)
    private String merchantName;

    /**
     * 商品名称
     */
    @TableField(exist = false)
    private String goodsName;

}
