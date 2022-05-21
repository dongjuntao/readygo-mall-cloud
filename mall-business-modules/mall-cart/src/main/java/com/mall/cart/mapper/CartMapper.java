package com.mall.cart.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.cart.entity.CartEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author DongJunTao
 * @Description 购物车mapper
 * @Date 2022/5/8 15:31
 * @Version 1.0
 */
@Mapper
public interface CartMapper extends BaseMapper<CartEntity> {

    List<CartEntity> getCartList(Long memberId);

    CartEntity getCart(Long memberId, Long merchantId);
}
