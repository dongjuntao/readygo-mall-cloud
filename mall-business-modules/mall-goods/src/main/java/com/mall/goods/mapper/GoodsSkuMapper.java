package com.mall.goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.goods.entity.GoodsSkuEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2021/6/30 17:13
 * @Version 1.0
 */
@Mapper
public interface GoodsSkuMapper extends BaseMapper<GoodsSkuEntity> {

    List<GoodsSkuEntity> getGoodsSkuList(Long[] skuIds);
}
