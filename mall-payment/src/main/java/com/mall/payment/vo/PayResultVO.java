package com.mall.payment.vo;

import lombok.Data;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2022/7/6 13:47
 * @Version 1.0
 */
@Data
public class PayResultVO {

    String code;

    String msg;

    String outTradeNo;

    String tradeStatus;
}
