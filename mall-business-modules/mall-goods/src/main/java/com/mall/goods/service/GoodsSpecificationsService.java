package com.mall.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.common.base.utils.PageUtil;
import com.mall.goods.entity.GoodsSpecificationsEntity;

import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 商品规格service
 * @Date 2021/6/25 16:32
 * @Version 1.0
 */
public interface GoodsSpecificationsService extends IService<GoodsSpecificationsEntity> {

    PageUtil queryPage(Map<String, Object> params);

    GoodsSpecificationsEntity getGoodsSpecificationsAndDetail(Long id);

    List<GoodsSpecificationsEntity> getGoodsSpecificationList();

}
