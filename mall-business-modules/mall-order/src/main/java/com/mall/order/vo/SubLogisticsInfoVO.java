package com.mall.order.vo;

import lombok.Data;
/**
 * @Author DongJunTao
 * @Description 商品发货接口参数
 * @Date 2023/7/27 10:29
 * @Version 1.0
 */
@Data
public class SubLogisticsInfoVO {

    private Long orderDetailId; //子订单id

    private String subLogisticsCompany;

    private String subTrackingNumber;
}
