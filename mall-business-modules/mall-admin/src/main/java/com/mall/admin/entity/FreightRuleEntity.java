package com.mall.admin.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author DongJunTao
 * @Description 运费规则表
 * @Date 2021/9/17 22:06
 * @Version 1.0
 */
@Data
@TableName("freight_rule")
public class FreightRuleEntity {

    @TableId
    private Long id;
    /**
     * 运费模板id
     */
    private Long freightTemplateId;
    /**
     * 区域集合（id:名称）
     */
    private String regionIdNames;
    /**
     * 首件(起始件数)或首重(起始重量)或首体积(起始体积)
     */
    private Double first;
    /**
     * 首件运费
     */
    private Double firstFreight;
    /**
     * 续件（数量）
     */
    private Double continuation;
    /**
     * 续件费用(每单位续件的费用)
     */
    private Double continuationFreight;
}
