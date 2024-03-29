package com.mall.cart.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.cart.dto.CartGoodsDTO;
import com.mall.cart.entity.CartEntity;
import com.mall.cart.vo.CartVO;

import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 购物车service
 * @Date 2022/5/8 15:33
 * @Version 1.0
 */
public interface CartService extends IService<CartEntity> {

    /**
     * 加入购物车
     * @return
     */
    int saveCart(Long memberId, Long merchantId, CartGoodsDTO cartGoodsDTO);

    List<CartEntity> listAll(Long memberId);

    CartEntity getCart(Long memberId, Long merchantId);

    int deleteBatch(List<Long> ids);

    int delete(Long id);

    /**
     * 获取购物车信息
     * @return
     */
    CartVO getCartInfo(Long memberId);
}
