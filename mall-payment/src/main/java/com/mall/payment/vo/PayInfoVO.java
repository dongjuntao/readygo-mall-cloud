package com.mall.payment.vo;

import lombok.*;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2022/6/26 15:48
 * @Version 1.0
 */
@Data
public class PayInfoVO {

    private String url;
    private String tradeCode;
    private String price;
    private String subject;
    private String body;

}
