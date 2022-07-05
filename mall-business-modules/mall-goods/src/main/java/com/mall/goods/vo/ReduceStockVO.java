package com.mall.goods.vo;

import lombok.Data;

/**
 * @Author DongJunTao
 * @Description 扣减库存VO
 * @Date 2022/7/2 14:59
 * @Version 1.0
 */
@Data
public class ReduceStockVO {

    private Long skuId;

    private Integer count;
}
