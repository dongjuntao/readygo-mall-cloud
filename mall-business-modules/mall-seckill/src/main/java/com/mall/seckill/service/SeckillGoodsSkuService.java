package com.mall.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.seckill.entity.SeckillGoodsSkuEntity;
import com.mall.seckill.vo.GoodsSkuVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 秒杀商品详细信息service
 * @Date 2022/1/6 9:44
 * @Version 1.0
 */
public interface SeckillGoodsSkuService extends IService<SeckillGoodsSkuEntity> {

    List<SeckillGoodsSkuEntity> getSeckillGoodsSkuListByIds(List<Long> seckillGoodsSkuIds);
}
