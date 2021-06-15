package com.mall.common.base.enums;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2021/5/27 14:22
 * @Version 1.0
 */
public enum MenuEnum {
    /**
     * 目录
     */
    CATALOG(0),
    /**
     * 菜单
     */
    MENU(1),
    /**
     * 按钮
     */
    BUTTON(2);

    private int value;

    MenuEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
