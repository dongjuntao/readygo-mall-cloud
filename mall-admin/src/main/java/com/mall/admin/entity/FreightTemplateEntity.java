package com.mall.admin.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author DongJunTao
 * @Description 运费模板表
 * @Date 2021/9/17 21:49
 * @Version 1.0
 */
@Data
@TableName("freight_template")
public class FreightTemplateEntity {

    @TableId
    private Long id;
    /**
     *  模板名称
     */
    private String name;
    /**
     * 模板类型 0:买家承担运费 1:卖家承担运费
     */
    private Integer type;
    /**
     * 计费方式 0:按数量 1:按金额 2:按体积
     */
    private Integer chargeType;
    /**
     * 是否是默认模板
     */
    private Boolean isDefault;
    /**
     * 是否开启默认运费（若开启默认运费，未设置的地区使用默认运费，否则，未设置的地区不支持配送）
     */
    private Boolean enableDefaultFreight;
    /**
     * 是否指定条件包邮
     */
    private Boolean enableConditionFree;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 创建人id
     */
    private Long createBy;
    /**
     * 修改人id
     */
    private Long updateBy;
    /**
     * 默认运费
     */
    @TableField(exist = false)
    private FreightDefaultEntity freightDefaultEntity;
    /**
     * 运费规则
     */
    @TableField(exist = false)
    private List<FreightRuleEntity> freightRuleEntityList;
    /**
     * 包邮规则
     */
    @TableField(exist = false)
    private List<FreightFreeRuleEntity> freightFreeRuleEntityList;
}
