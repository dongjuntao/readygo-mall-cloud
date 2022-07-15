package com.mall.payment.vo;

import lombok.Data;

/**
 * @Author DongJunTao
 * @Description 调用获取支付二维码信息VO
 * @Date 2022/7/11 10:32
 * @Version 1.0
 */
@Data
public class CreateQrParamsVO {

    private String tradeCode;

    private String price;

    private String subject;
}
