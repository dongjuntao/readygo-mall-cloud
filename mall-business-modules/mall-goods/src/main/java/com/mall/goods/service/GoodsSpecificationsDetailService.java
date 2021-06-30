package com.mall.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.goods.entity.GoodsSpecificationsDetailEntity;
import com.mall.goods.entity.GoodsSpecificationsEntity;

import java.util.List;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2021/6/29 13:11
 * @Version 1.0
 */
public interface GoodsSpecificationsDetailService extends IService<GoodsSpecificationsDetailEntity> {

    void deleteBatch(List<Long> ids);
}
