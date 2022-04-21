package com.mall.admin.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author DongJunTao
 * @Description 包邮规则表
 * @Date 2021/9/17 22:08
 * @Version 1.0
 */
@Data
@TableName("freight_free_rule")
public class FreightFreeRuleEntity {
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
     * 包邮条件（0：满金额包邮，1:满（件数，重量，体积）包邮： 2:满金额或满（件数，重量，体积） 包邮，3:满金额且满（件数，重量，体积）包邮
     */
    private Integer freeCondition;
    /**
     * 需满金额
     */
    private Integer money;
    /**
     * 需满数量（件数，重量，体积）
     */
    private Integer quantity;
}
