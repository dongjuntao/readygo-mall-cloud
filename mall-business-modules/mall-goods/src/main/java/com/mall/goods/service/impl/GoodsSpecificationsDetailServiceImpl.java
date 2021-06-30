package com.mall.goods.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.goods.entity.GoodsSpecificationsDetailEntity;
import com.mall.goods.entity.GoodsSpecificationsEntity;
import com.mall.goods.mapper.GoodsSpecificationsDetailMapper;
import com.mall.goods.service.GoodsSpecificationsDetailService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2021/6/29 13:12
 * @Version 1.0
 */
@Service("goodsSpecificationDetailService")
public class GoodsSpecificationsDetailServiceImpl extends ServiceImpl<GoodsSpecificationsDetailMapper,
        GoodsSpecificationsDetailEntity> implements GoodsSpecificationsDetailService {

    /**
     * 根据商品规格信息表id批量删除
     * @param ids
     */
    @Override
    public void deleteBatch(List<Long> ids) {
        baseMapper.deleteBatchIds(ids);
    }
}
