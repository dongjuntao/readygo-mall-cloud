package com.mall.order.vo;

import lombok.Data;

import java.util.List;

/**
 * @Author DongJunTao
 * @Description 商品发货接口参数
 * @Date 2023/7/26 17:11
 * @Version 1.0
 */
@Data
public class OrderShipmentParamsVO {

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 是否拆分订单发货
     */
    private Boolean isSplit;

    /**
     * 不分开发货时，多个子订单为同一个物流公司
     */
    private String logisticsCompany;

    /**
     * 不分开发货时，多个子订单为同一个物流单号
     */
    private String trackingNumber;

    /**
     * 子订单物流信息列表
     */
    private List<SubLogisticsInfoVO> subLogisticsInfoList;

}