package com.mall.order.vo;

import lombok.Data;

/**
 * @Author DongJunTao
 * @Description 近六个月订单量统计
 * @Date 2023/7/11 10:46
 * @Version 1.0
 */
@Data
public class RecentSixMonthOrderCountVO {

    private String yearMonth; //如 2023-01

    private Integer count; //订单量
}
