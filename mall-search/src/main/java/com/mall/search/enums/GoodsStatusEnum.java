package com.mall.search.enums;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2023/6/19 14:08
 * @Version 1.0
 */
public enum GoodsStatusEnum {

    NEW_CREATED("NEW_CREATED"), //新创建
    AUDIT("AUDIT"), //待审核
    AUDIT_FAILED("AUDIT_FAILED"), //审核不通过
    ON_SALE("ON_SALE"), //审核通过（已上架）
    NOT_ON_SALE("NOT_ON_SALE"); //已下架

    private String goodsStatus;

    GoodsStatusEnum(String goodsStatus) {
        this.goodsStatus = goodsStatus;
    }

    public String getGoodsStatus() {
        return goodsStatus;
    }

    public void setGoodsStatus(String goodsStatus) {
        this.goodsStatus = goodsStatus;
    }
}
