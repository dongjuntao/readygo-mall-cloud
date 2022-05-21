package com.mall.cart.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.cart.entity.CartGoodsEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author DongJunTao
 * @Description 购物车商品信息mapper
 * @Date 2022/5/8 15:32
 * @Version 1.0
 */
@Mapper
public interface CartGoodsMapper extends BaseMapper<CartGoodsEntity> {
}
