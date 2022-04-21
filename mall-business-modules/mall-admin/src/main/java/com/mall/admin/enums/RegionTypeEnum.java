package com.mall.admin.enums;

import lombok.Data;

/**
 * @Author DongJunTao
 * @Description 区域类型枚举
 * @Date 2021/8/14 23:02
 * @Version 1.0
 */
public enum RegionTypeEnum {
    province,
    city,
    area;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
