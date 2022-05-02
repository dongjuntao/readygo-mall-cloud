package com.mall.member.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.member.entity.CollectGoodsEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author DongJunTao
 * @Description 商品收藏mapper
 * @Date 2022/5/2 10:50
 * @Version 1.0
 */
@Mapper
public interface CollectGoodsMapper extends BaseMapper<CollectGoodsEntity> {
}
