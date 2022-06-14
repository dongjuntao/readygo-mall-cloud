package com.mall.order.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author DongJunTao
 * @Description 支付结算信息
 * @Date 2022/5/28 15:55
 * @Version 1.0
 */
@Data
public class PayVO {

    private Integer totalCount;
    /**
     * 已选的商品总价
     */
    private BigDecimal totalPrice;

    /**
     * 最终价格（去掉优惠和运费后的价格）
     */
    private BigDecimal finalPrice;
    /**
     * 优惠价钱
     */
    private BigDecimal discountPrice;

    /**
     * 总运费
     */
    private BigDecimal totalFreight;
    /**
     * 商家信息
     */
    private List<PayMerchantVO> payMerchantList;

}
