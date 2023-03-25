package com.mall.search.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2023/3/10 16:11
 * @Version 1.0
 */
@Data
public class ESSeckillInfo {
    /**
     * 秒杀价
     */
    private BigDecimal seckillPrice;
    /**
     * 秒杀库存
     */
    private Integer seckillStock;
}
