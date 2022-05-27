package com.mall.cart.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.cart.constant.RedisExpiresTimeConstant;
import com.mall.cart.constant.RedisKeyConstant;
import com.mall.cart.dto.CartGoodsDTO;
import com.mall.cart.entity.CartEntity;
import com.mall.cart.entity.CartGoodsEntity;
import com.mall.cart.mapper.CartGoodsMapper;
import com.mall.cart.mapper.CartMapper;
import com.mall.cart.service.CartService;
import com.mall.common.redis.util.RedisUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2022/5/8 15:35
 * @Version 1.0
 */
@Service("cartService")
public class CartServiceImpl extends ServiceImpl<CartMapper, CartEntity> implements CartService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private CartGoodsMapper cartGoodsMapper;

    @Override
    @Transactional
    public int saveCart(Long memberId, Long merchantId, CartGoodsDTO cartGoodsDTO) {
        QueryWrapper<CartEntity> cartQueryWrapper = new QueryWrapper<>();
        cartQueryWrapper.eq(memberId != null, "member_id", memberId);
        cartQueryWrapper.eq(merchantId != null, "merchant_id", merchantId);
        //查找是否已经存在，如果存在，不需要添加
        CartEntity cart = cartMapper.selectOne(cartQueryWrapper);
        if (cart == null) {
            cart = new CartEntity();
            cart.setMemberId(memberId);
            cart.setMerchantId(merchantId);
            //保存购物车
            cartMapper.insert(cart);
        }
        QueryWrapper<CartGoodsEntity> cartGoodsQueryWrapper = new QueryWrapper();
        cartGoodsQueryWrapper.eq(cart.getId() != null, "cart_id", cart.getId());
        cartGoodsQueryWrapper.eq(cartGoodsDTO.getGoodsId() != null, "goods_id", cartGoodsDTO.getGoodsId());
        cartGoodsQueryWrapper.eq(cartGoodsDTO.getGoodsSkuId() != null, "goods_sku_id", cartGoodsDTO.getGoodsSkuId());
        CartGoodsEntity cartGoods = cartGoodsMapper.selectOne(cartGoodsQueryWrapper);
        if (cartGoods == null) {
            cartGoods = new CartGoodsEntity();
            BeanUtils.copyProperties(cartGoodsDTO, cartGoods);
            cartGoods.setChecked(false);//设置默认不选中
            cartGoods.setCreateTime(new Date());
            cartGoods.setCartId(cart.getId());
            cartGoodsMapper.insert(cartGoods);
        } else {
            return -1; //已经添加到购物车
        }

        //保存购物车商品信息
        return 1;
    }

    @Override
    public List<CartEntity> listAll(Map<String, Object> params) {
        Long memberId = params.get("memberId") == null ? null: Long.valueOf((params.get("memberId").toString()));
        //先从redis中查询，找不到，再从数据库中查询
        Object object = RedisUtil.hGet(RedisKeyConstant.CART_KEY, String.valueOf(memberId));
        List<CartEntity> cartList;
        if (object == null) {
            //查一次数据库，放入redis
            cartList = cartMapper.getCartList(memberId);
            RedisUtil.hSet(RedisKeyConstant.CART_KEY, String.valueOf(memberId), cartList, RedisExpiresTimeConstant.CART_EXPIRED_TIME);
            return cartList;
        }
        cartList = (List<CartEntity>) object;
        return cartList;
    }

    @Override
    public CartEntity getCart(Long memberId, Long merchantId) {
        return cartMapper.getCart(memberId, merchantId);
    }

    @Override
    public int deleteBatch(List<Long> ids) {
        return baseMapper.deleteBatchIds(ids);
    }

    @Override
    public int delete(Long id) {
        return baseMapper.deleteById(id);
    }
}
