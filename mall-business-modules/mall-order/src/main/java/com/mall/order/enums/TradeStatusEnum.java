package com.mall.order.enums;

/**
 * @Author DongJunTao
 * @Description 交易状态
 * @Date 2022/6/18 21:43
 * @Version 1.0
 */
public enum TradeStatusEnum {

    /**
     * 交易状态
     */
    UNPAID("待付款"),
    PAID("已付款"),
    CANCEL("已取消"),
    FINISHED("已完成");

    private String description;

    TradeStatusEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
