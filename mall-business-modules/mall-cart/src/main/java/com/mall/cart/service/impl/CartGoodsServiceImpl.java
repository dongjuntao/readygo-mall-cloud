package com.mall.cart.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.cart.dto.CartGoodsDTO;
import com.mall.cart.entity.CartGoodsEntity;
import com.mall.cart.mapper.CartGoodsMapper;
import com.mall.cart.service.CartGoodsService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2022/5/8 15:36
 * @Version 1.0
 */
@Service("cartGoodsService")
public class CartGoodsServiceImpl extends ServiceImpl<CartGoodsMapper, CartGoodsEntity> implements CartGoodsService {

    /**
     * 批量更新
     * @param cartGoodsList
     * @return
     */
    @Override
    public boolean updateBatch(List<CartGoodsEntity> cartGoodsList) {
        return this.saveOrUpdateBatch(cartGoodsList);
    }

    @Override
    public CartGoodsEntity getById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    public int delete(Long id) {
        return baseMapper.deleteById(id);
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @Override
    public int deleteBatch(List<Long> ids) {
        return baseMapper.deleteBatchIds(ids);
    }

    @Override
    public List<CartGoodsEntity> getByParams(Map<String, Object> params) {
        Long cartId = params.get("cartId") == null ? null: Long.valueOf((params.get("cartId").toString()));
        QueryWrapper<CartGoodsEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(cartId != null, "cart_id", cartId);
        return baseMapper.selectList(queryWrapper);
    }
}
