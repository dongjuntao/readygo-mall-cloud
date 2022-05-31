package com.mall.cart.vo;

import lombok.Data;

import java.util.List;

/**
 * @Author DongJunTao
 * @Description 支付页商家信息
 * @Date 2022/5/28 16:01
 * @Version 1.0
 */
@Data
public class PayMerchantVO {

    /**
     * 商家id
     */
    private Long merchantId;

    /**
     * 商家名称
     */
    private String merchantName;

    /**
     * 订单备注
     */
    private String remark;

    /**
     * 商品信息
     */
    private List<PayGoodsVO> payGoodsList;
}
