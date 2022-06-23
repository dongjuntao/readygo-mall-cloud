package com.mall.admin.enums;

/**
 * @Author DongJunTao
 * @Description 支付方式枚举
 * @Date 2022/6/19 15:31
 * @Version 1.0
 */
public enum PayTypeEnum {

    ALI_PAY("支付宝"),
    WECHAT_PAY("微信");

    PayTypeEnum(String description) {
        this.description = description;
    }
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
