package com.mall.admin.enums;

/**
 * @Author DongJunTao
 * @Description 订单状态枚举（父订单和子订单共用）
 * @Date 2022/6/15 21:13
 * @Version 1.0
 */

public enum OrderStatusEnum {
    UNPAID("待付款"),
    PAID("已付款"),
    UNDELIVERED("待发货"),
    DELIVERED("已发货"),
    FINISHED("已完成"),
    CANCELLED("已取消");

    OrderStatusEnum(String description) {
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
