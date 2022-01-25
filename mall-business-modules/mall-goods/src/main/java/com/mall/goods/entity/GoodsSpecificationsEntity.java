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
 * @Description 商品规格表
 * @Date 2021/6/22 11:10
 * @Version 1.0
 */
@Data
@TableName("goods_specifications")
public class GoodsSpecificationsEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;
    /**
     * 规格名称
     */
    private String name;
    /**
     * 规格描述
     */
    private String description;
    /**
     * 所属商户
     */
    private Long adminUserId;
    /**
     * 商品规格详细信息列表
     */
    @TableField(exist=false)
    private List<GoodsSpecificationsDetailEntity> goodsSpecificationsDetailEntityList;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建时间
     */
    private Date updateTime;
}
