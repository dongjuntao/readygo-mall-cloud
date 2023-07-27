package com.mall.payment.enums;

/**
 * @Author DongJunTao
 * @Description 交易和订单号前缀
 * @Date 2022/7/11 17:09
 * @Version 1.0
 */
public enum CodePrefixEnum {

    /**
     * 前缀信息
     */
    T("交易号前缀"),
    O("订单号前缀"),
    S("子订单号前缀");

    private String description;

    CodePrefixEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
