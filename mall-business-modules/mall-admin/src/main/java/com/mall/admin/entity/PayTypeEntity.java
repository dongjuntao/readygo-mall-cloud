package com.mall.admin.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mall.admin.enums.PayTypeEnum;
import lombok.Data;

import java.util.Date;

/**
 * @Author DongJunTao
 * @Description 支付管理（支付方式）
 * @Date 2022/6/19 14:41
 * @Version 1.0
 */
@Data
@TableName("pay_type")
public class PayTypeEntity {

    @TableId
    private Long id;

    /**
     * 支付方式的名称
     */
    private String name;

    /**
     * 图标
     */
    private String logo;

    /**
     * 支付方式
     */
    private PayTypeEnum type;

    /**
     * 支付方式描述
     */
    private String description;

    /**
     * 是否开启使用
     */
    private Boolean enable;

    /**
     * 创建时间
     */
    private Date createTime;
}
