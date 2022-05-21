package com.mall.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.goods.entity.GoodsSkuEntity;

import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2021/6/30 17:13
 * @Version 1.0
 */
public interface GoodsSkuService extends IService<GoodsSkuEntity> {

    void deleteBatch(List<Long> ids);

    List<GoodsSkuEntity> getGoodsSkuList(Long[] skuIds);
}
