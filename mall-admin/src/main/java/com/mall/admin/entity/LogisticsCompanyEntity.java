package com.mall.admin.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2021/9/7 21:13
 * @Version 1.0
 */
@Data
@TableName("logistics_company")
public class LogisticsCompanyEntity {

    @TableId
    private Long id;
    /**
     * 物流公司名称
     */
    private String name;
    /**
     * 物流公司简称
     */
    private String abbreviation;
    /**
     * 物流公司编码
     */
    private String code;
    /**
     * 物流公司网址
     */
    private String url;
    /**
     * 排序 越小越靠前
     */
    private Integer orderNum;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 商家是否启用了此快递公司
     */
    @TableField(exist = false)
    private Boolean enable;
}
