package com.mall.order.vo;

import lombok.Data;

/**
 * @Author DongJunTao
 * @Description 收货人信息
 * @Date 2022/5/28 16:57
 * @Version 1.0
 */
@Data
public class RecipientInfoVO {

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
     * 收货地区名称（省、市、区县）如（安徽省 淮南市 寿县）
     */
    private String regionNames;

    private Boolean selected;
}
