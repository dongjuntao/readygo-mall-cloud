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
     * 根据skuId集合查询秒杀商品列表
     * @param seckillGoodsSkuIds
     * @return
     */
    List<SeckillGoodsSkuEntity> getSeckillGoodsSkuListByIds(List<Long> seckillGoodsSkuIds);

}
