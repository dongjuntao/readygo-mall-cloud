package com.mall.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.goods.entity.GoodsSkuEntity;
import com.mall.goods.mapper.GoodsSkuMapper;
import com.mall.goods.service.GoodsSkuService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2021/6/30 17:14
 * @Version 1.0
 */
@Service("goodsSkuService")
public class GoodsSkuServiceImpl extends ServiceImpl<GoodsSkuMapper, GoodsSkuEntity>
        implements GoodsSkuService {

    @Override
    public void deleteBatch(List<Long> ids) {
        baseMapper.deleteBatchIds(ids);
    }

    /**
     * 根据skuIds查询商品sku信息
     * @param skuIds
     * @return
     */
    @Override
    public List<GoodsSkuEntity> getGoodsSkuList(Long[] skuIds) {
        return baseMapper.getGoodsSkuList(skuIds);
    }
}
