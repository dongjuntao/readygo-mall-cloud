package com.mall.member.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Author DongJunTao
 * @Description 收货信息表
 * @Date 2022/4/16 18:04
 * @Version 1.0
 */
@Data
@TableName("recipient_info")
public class RecipientInfoEntity {

    @TableId
    private Long id;
    /**
     * 会员id
     */
    private Long memberId;
    /**
     * 收件人姓名
     */
    private String name;
    /**
     * 详细地址
     */
    private String detailAddress;
    /**
     * 收件人手机号码
     */
    private String mobile;
    /**
     * 收货地区id（省、市、区县）如（100,101,102）
     */
    private String regions;
    /**
     * 地址别名（家，学校，公司）
     */
    private String addressAlias;
    /**
     * 是否默认
     */
    private Boolean isDefault;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 收货地区名称（省、市、区县）如（安徽省 淮南市 寿县）
     */
    @TableField(exist = false)
    private String regionNames;
}
