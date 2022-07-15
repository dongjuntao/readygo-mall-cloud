package com.mall.order.enums;

/**
 * @Author DongJunTao
 * @Description 支付方式枚举
 * @Date 2022/7/8 15:55
 * @Version 1.0
 */
public enum PayTypeEnum {
    /**
     * 支付方式
     */
    ALIPAY("支付宝支付"),
    WECHAT_PAY("微信支付");

    private String description;

    PayTypeEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
