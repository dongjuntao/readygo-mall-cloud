package com.mall.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.common.util.PageUtil;
import com.mall.goods.entity.BrandEntity;

import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 品牌service
 * @Date 2021/6/16 9:57
 * @Version 1.0
 */
public interface BrandService extends IService<BrandEntity> {

    PageUtil queryPage(Map<String, Object> params);

    BrandEntity queryByParams(Map<String, Object> params);
}
