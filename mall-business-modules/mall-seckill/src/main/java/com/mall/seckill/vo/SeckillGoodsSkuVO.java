package com.mall.seckill.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2022/1/7 15:05
 * @Version 1.0
 */
@Data
public class SeckillGoodsSkuVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 关联秒杀配置表id
     */
    private Long seckillConfigId;
    /**
     * 商品sku id
     */
    private Long goodsSkuId;
    /**
     * 秒杀价
     */
    private BigDecimal seckillPrice;
    /**
     * 秒杀库存
     */
    private Integer seckillStock;

}
