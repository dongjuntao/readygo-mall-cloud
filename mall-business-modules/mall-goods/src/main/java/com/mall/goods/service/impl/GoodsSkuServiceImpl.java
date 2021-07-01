package com.mall.goods.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.goods.entity.GoodsSkuEntity;
import com.mall.goods.mapper.GoodsSkuMapper;
import com.mall.goods.service.GoodsSkuService;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
