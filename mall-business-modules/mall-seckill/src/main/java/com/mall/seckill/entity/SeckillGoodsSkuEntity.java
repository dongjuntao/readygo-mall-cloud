package com.mall.seckill.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author DongJunTao
 * @Description 秒杀商品详细信息
 * @Date 2022/1/5 22:09
 * @Version 1.0
 */
@Data
@TableName("seckill_goods_sku")
public class SeckillGoodsSkuEntity {

    @TableId
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
