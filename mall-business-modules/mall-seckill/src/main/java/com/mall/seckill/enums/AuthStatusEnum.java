package com.mall.seckill.enums;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2023/7/13 20:08
 * @Version 1.0
 */
public enum AuthStatusEnum {

    NEW_CREATED("NEW_CREATED"), //新创建
    AUDIT("AUDIT"), //待审核
    AUDIT_FAILED("AUDIT_FAILED"), //审核不通过
    AUDIT_SUCCESS("AUDIT_SUCCESS"), //审核通过（已上架）
    CANCELED("CANCELED"); //已取消

    private String authStatus;

    AuthStatusEnum(String authStatus) {
        this.authStatus = authStatus;
    }

    public String getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(String authStatus) {
        this.authStatus = authStatus;
    }
}
