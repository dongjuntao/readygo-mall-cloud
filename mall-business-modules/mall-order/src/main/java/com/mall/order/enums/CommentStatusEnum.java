package com.mall.order.enums;

/**
 * @Author DongJunTao
 * @Description 订单评价 状态
 * @Date 2022/7/17 10:36
 * @Version 1.0
 */
public enum CommentStatusEnum {

    NEW("新订单，无法评价"),
    NOT_COMMENTED("待评价"),
    COMMENTED("已评价（可追评）"),
    NOT_ALLOW_COMMENT("不允许评价");

    private String description;

    CommentStatusEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
