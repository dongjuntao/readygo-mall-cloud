package com.mall.order.enums;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2022/7/19 14:57
 * @Version 1.0
 */
public enum CartTypeEnum {

    /**
     * 前缀信息
     */
    CART("加入购物车购买"),
    BUY_NOW("立即购买");

    private String description;

    CartTypeEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
