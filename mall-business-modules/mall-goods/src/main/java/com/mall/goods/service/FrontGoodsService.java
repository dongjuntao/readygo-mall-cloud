package com.mall.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.common.base.utils.PageUtil;
import com.mall.goods.entity.GoodsEntity;

import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 商品service 门户端使用
 * @Date 2023/4/13 13:21
 * @Version 1.0
 */
public interface FrontGoodsService extends IService<GoodsEntity> {

    GoodsEntity getGoodsAndSku(Long id);

    PageUtil queryPage(Map<String, Object> params);

    /**
     * 根据商品id集合，查询商品【包含sku信息】
     * @param goodsIds
     * @return
     */
    List<GoodsEntity> getByGoodsIds(Long[] goodsIds);
}
