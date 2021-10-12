package com.mall.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.common.util.PageUtil;
import com.mall.goods.entity.GoodsEntity;

import java.util.Map;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2021/6/30 16:53
 * @Version 1.0
 */
public interface GoodsService extends IService<GoodsEntity> {

    PageUtil queryPage(Map<String, Object> params);

    GoodsEntity getGoodsAndSku(Long id);

    int updateOnSale(Long goodsId, Boolean onSale);
}
