package com.mall.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.goods.entity.GoodsSkuEntity;
import com.mall.goods.mapper.GoodsSkuMapper;
import com.mall.goods.service.GoodsSkuService;
import com.mall.goods.vo.ReduceStockVO;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    /**
     * 库存扣减
     * @param reduceStock
     */
    @Override
    public void reduceStock(ReduceStockVO reduceStock) {
        QueryWrapper<GoodsSkuEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(reduceStock.getSkuId() != null, "id", reduceStock.getSkuId());
        //找到具体的商品sku
        GoodsSkuEntity goodsSkuEntity = baseMapper.selectOne(queryWrapper);
        //库存扣减
        goodsSkuEntity.setStock(goodsSkuEntity.getStock() - reduceStock.getCount());
        baseMapper.updateById(goodsSkuEntity);
    }

    /**
     * 批量扣减库存
     * @param reduceStockList
     */
    @Override
    @Transactional
    public void batchReduceStock(List<Map<String, Object>> reduceStockList) {
        List<GoodsSkuEntity> goodsSkuList = new ArrayList<>();
        for (Map<String,Object> reduceStock : reduceStockList) {
            QueryWrapper<GoodsSkuEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq(reduceStock.get("skuId") != null, "id", Long.valueOf(String.valueOf(reduceStock.get("skuId"))));
            GoodsSkuEntity goodsSku = baseMapper.selectOne(queryWrapper);

            //判断库存是否够用，不够，直接抛出异常，事务会回滚
            Integer oldStock = goodsSku.getStock();
            Integer reduceCount = Integer.valueOf(String.valueOf(reduceStock.get("count")));
            if (oldStock < reduceCount) {
                throw new RuntimeException("rest stock is not enough");
            }

            //库存扣减
            goodsSku.setStock(goodsSku.getStock() - Integer.valueOf(String.valueOf(reduceStock.get("count"))));
            goodsSkuList.add(goodsSku);
        }
        this.updateBatchById(goodsSkuList);
    }
}
