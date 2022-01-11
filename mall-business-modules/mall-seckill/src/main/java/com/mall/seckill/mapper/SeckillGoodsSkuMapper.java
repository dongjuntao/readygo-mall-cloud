package com.mall.seckill.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.seckill.entity.SeckillGoodsSkuEntity;
import com.mall.seckill.vo.GoodsSkuVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2022/1/6 9:25
 * @Version 1.0
 */
@Mapper
public interface SeckillGoodsSkuMapper extends BaseMapper<SeckillGoodsSkuEntity> {

    /**
     * 查询秒杀商品相关信息(关联商品sku)
     * @return
     */
    List<GoodsSkuVO> getGoodsSkuListById(@Param("seckillConfigId") Long seckillConfigId,
                             @Param("goodsId") Long goodsId);
}
