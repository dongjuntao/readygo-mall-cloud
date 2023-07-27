package com.mall.payment.enums;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2023/7/17 16:09
 * @Version 1.0
 */
public enum OrderTypeEnum {

    /**
     * 前缀信息
     */
    TRADE("交易类型"),
    ORDER("订单类型");

    private String description;

    OrderTypeEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
