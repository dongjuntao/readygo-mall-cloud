package com.mall.cart.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.cart.dto.CartGoodsDTO;
import com.mall.cart.entity.CartGoodsEntity;

import java.util.List;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2022/5/8 15:33
 * @Version 1.0
 */
public interface CartGoodsService extends IService<CartGoodsEntity> {

    /**
     * 批量更新
     * @param cartGoodsList
     * @return
     */
    boolean updateBatch(List<CartGoodsEntity> cartGoodsList);

    CartGoodsEntity getById(Long id);

}
