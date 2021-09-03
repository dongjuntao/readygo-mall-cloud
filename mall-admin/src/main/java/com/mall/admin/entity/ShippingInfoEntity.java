package com.mall.admin.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Author DongJunTao
 * @Description 发货相关信息
 * @Date 2021/8/31 21:43
 * @Version 1.0
 */
@Data
@TableName("shipping_info")
public class ShippingInfoEntity {
    @TableId
    private Long id;
    /**
     * 发货人姓名
     */
    private String name;
    /**
     * 发货人性别
     */
    private Integer sex;
    /**
     * 发货地区id（省、市、区县）如（100,101,102）
     */
    private String regions;
    /**
     * 发货详细地址
     */
    private String address;
    /**
     * 邮编
     */
    private String postalCode;
    /**
     * 手机号码
     */
    private String mobile;
    /**
     * 固定电话
     */
    private String fixedTelephone;
    /**
     * 所属商家
     */
    private Long adminUserId;
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
     * 是否默认
     */
    private Boolean isDefault;
    /**
     * 所属商户（关联查询）
     */
    @TableField(exist = false)
    private String merchant;
    /**
     * 发货地区名称（省、市、区县）如（安徽省 淮南市 寿县）
     */
    @TableField(exist = false)
    private String regionNames;

}
