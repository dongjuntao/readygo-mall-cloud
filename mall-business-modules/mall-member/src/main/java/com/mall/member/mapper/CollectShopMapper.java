package com.mall.member.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.member.entity.CollectGoodsEntity;
import com.mall.member.entity.CollectShopEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author DongJunTao
 * @Description 店铺收藏mapper
 * @Date 2022/5/2 10:50
 * @Version 1.0
 */
@Mapper
public interface CollectShopMapper extends BaseMapper<CollectShopEntity> {
}
