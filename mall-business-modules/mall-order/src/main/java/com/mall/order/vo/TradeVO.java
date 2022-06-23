package com.mall.order.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2022/6/18 22:18
 * @Version 1.0
 */
@Data
public class TradeVO {
    /**
     * 交易时间
     */
    private Date tradeTime;
    /**
     * 可以超时的时间
     */
    private Date overtime;
    /**
     * 最终金额（应付金额）
     */
    private BigDecimal finalPrice;

    /**
     * 支付方式
     */
    private List<PayTypeVO> payTypeList;
}
