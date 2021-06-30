package com.mall.goods.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.goods.entity.GoodsEntity;
import com.mall.goods.mapper.GoodsMapper;
import com.mall.goods.service.GoodsService;
import org.springframework.stereotype.Service;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2021/6/30 16:54
 * @Version 1.0
 */
@Service("goodsService")
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, GoodsEntity> implements GoodsService {
}
