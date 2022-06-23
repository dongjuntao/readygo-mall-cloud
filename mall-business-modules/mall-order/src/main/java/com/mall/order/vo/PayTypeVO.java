package com.mall.order.vo;

import lombok.Data;

/**
 * @Author DongJunTao
 * @Description 支付方式
 * @Date 2022/6/20 22:12
 * @Version 1.0
 */
@Data
public class PayTypeVO {
    /**
     * 支付名称
     */
    private String name;

    /**
     * 支付类型
     */
    private String type;

    /**
     * 图标
     */
    private String logo;
}
