package com.mall.goods.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2022/9/26 16:56
 * @Version 1.0
 */
@Data
public class SeckillInfo {

    /**
     * 秒杀价
     */
    private BigDecimal seckillPrice;
    /**
     * 秒杀库存
     */
    private Integer seckillStock;
}
