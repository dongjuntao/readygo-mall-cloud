package com.mall.order.enums;

/**
 * @Author DongJunTao
 * @Description 售后状态
 * @Date 2022/7/17 10:28
 * @Version 1.0
 */
public enum AfterSalesStatusEnum {

    NEW("新订单，无法申请售后"),
    NOT_APPLIED("未申请售后"),
    APPLIED("已申请售后"),
    NOT_ALLOW_APPLY("不允许申请售后");

    private String description;

    AfterSalesStatusEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
