package com.mall.cart.controller;

import com.mall.admin.api.feign.front.FeignAdminUserService;
import com.mall.cart.dto.CartGoodsDTO;
import com.mall.cart.entity.CartEntity;
import com.mall.cart.entity.CartGoodsEntity;
import com.mall.cart.service.CartGoodsService;
import com.mall.cart.service.CartService;
import com.mall.cart.vo.*;
import com.mall.common.base.CommonResult;
import com.mall.common.base.enums.ResultCodeEnum;
import com.mall.common.base.utils.CurrentUserContextUtil;
import com.mall.goods.api.FeignFrontGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author DongJunTao
 * @Description 购物车controller
 * @Date 2022/5/8 16:00
 * @Version 1.0
 */
@RestController
@RequestMapping( "cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartGoodsService cartGoodsService;

    /**
     * 加入购物车
     * @param merchantId 商家id
     * @param cartGoodsDTO 商品相关信息
     * @return
     */
    @PostMapping("save")
    public CommonResult save(@RequestParam("merchantId") Long merchantId,
                             @RequestBody CartGoodsDTO cartGoodsDTO) {
        Long userId = CurrentUserContextUtil.getCurrentUserInfo().getUserId();
        int num = cartService.saveCart(userId, merchantId, cartGoodsDTO);
        //已经添加到购物车，给出提示
        if (num == -1) {
            return CommonResult.fail(ResultCodeEnum.CART_IS_EXIST.getCode(),ResultCodeEnum.CART_IS_EXIST.getMessage());
        }
        return CommonResult.success();
    }

    /**
     * 查询会员购物车信息
     */
    @GetMapping("/list")
    public CommonResult list() {
        CartVO cartVO = cartService.getCartInfo(CurrentUserContextUtil.getCurrentUserInfo().getUserId());
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getMessage(), cartVO);
    }

    /**
     * 选中时触发
     * @param type 0：全选， 1：店铺全选 2：单个选中
     * @param checked true：选中 false：取消选中
     * @param bizId 0：无需传递， 1：店铺id 2：cartGoodsId
     * @return
     */
    @PostMapping("setChecked")
    public CommonResult setChecked(@RequestParam("type") Integer type,
                                   @RequestParam("checked") Boolean checked,
                                   @RequestParam(value = "bizId", required = false) Long bizId) {
        Long memberId = CurrentUserContextUtil.getCurrentUserInfo().getUserId();
        if (type == 0) {
            List<CartEntity> cartList = cartService.listAll(CurrentUserContextUtil.getCurrentUserInfo().getUserId());
            List<CartGoodsEntity> cartGoodsList = new ArrayList<>();
            if (!CollectionUtils.isEmpty(cartList)) {
                for (int i=0;i<cartList.size(); i++) {
                    cartList.get(i).getCartGoodsList().forEach(cartGood ->{
                        cartGood.setChecked(checked);
                    });
                    cartGoodsList.addAll(cartList.get(i).getCartGoodsList());
                }
            }
            //批量更新
            cartGoodsService.updateBatch(cartGoodsList);
        } else if(type == 1) {
            CartEntity cart = cartService.getCart(memberId, bizId);
            List<CartGoodsEntity> cartGoodsList = new ArrayList<>();
            if (cart != null) {
                cart.getCartGoodsList().forEach(cartGoods -> {
                    cartGoods.setChecked(checked);
                });
                cartGoodsList.addAll(cart.getCartGoodsList());
            }
            //批量更新
            cartGoodsService.updateBatch(cartGoodsList);
        } else if (type == 2) {
            List<CartGoodsEntity> cartGoodsList = new ArrayList<>();
            CartGoodsEntity cartGoods = cartGoodsService.getById(bizId);
            if (cartGoods != null) {
                cartGoods.setChecked(checked);
                cartGoodsList.add(cartGoods);
                cartGoodsService.updateBatch(cartGoodsList);
            }
        }

        return CommonResult.success();
    }

    /**
     * 设置购买数量
     * @param count 购买数量
     * @param cartGoodsId 购物车关联的商品信息id
     * @return
     */
    @PostMapping("setCount")
    public CommonResult setCount(@RequestParam("count") Integer count,
                                 @RequestParam("cartGoodsId") Long cartGoodsId) {
        List<CartGoodsEntity> cartGoodsList = new ArrayList<>();
        CartGoodsEntity cartGoods = cartGoodsService.getById(cartGoodsId);
        cartGoods.setCount(count);
        cartGoodsList.add(cartGoods);
        cartGoodsService.updateBatch(cartGoodsList);
        return CommonResult.success();
    }

    /**
     * 删除购物车信息
     * @param deleteType 0：单个删除  1：选中删除 2：清空购物车
     * @param cartGoodsId 单个删除时，传递商品sku信息id
     * @return
     */
    @DeleteMapping("deleteCart")
    @Transactional
    public CommonResult deleteCart(@RequestParam("deleteType") Integer deleteType,
                                   @RequestParam(value = "cartGoodsId", required = false) Long cartGoodsId) {
        Long memberId = CurrentUserContextUtil.getCurrentUserInfo().getUserId();
        //单个删除
        if (deleteType == 0) {
            CartGoodsEntity cartGoods = cartGoodsService.getById(cartGoodsId);
            Long cartId = cartGoods.getCartId(); //商品id
            Map<String, Object> params = new HashMap<>();
            params.put("cartId", cartId);
            List<CartGoodsEntity> cartGoodsList = cartGoodsService.getByParams(params);
            //如果只有一个，需删除商家信息，否则，只需要删除购物车商品信息
            if (cartGoodsList.size() == 1) {
                cartGoodsService.delete(cartGoodsId);
                //再删除商家信息
                cartService.delete(cartId);
            } else if (cartGoodsList.size()>1) {
                cartGoodsService.delete(cartGoodsId);
            }
        } else if (deleteType == 1) { //选中删除
            List<CartEntity> cartList = cartService.listAll(memberId);
            List<Long> deleteCartIdList = new ArrayList<>();
            for (CartEntity cart : cartList) {
                List<CartGoodsEntity> cartGoodsList = cart.getCartGoodsList();
                List<Long> deleteGoodsIdList = new ArrayList<>();
                Boolean isDeleteMerchant = true;
                //找到需要删除的商品，该商户下的商品全部删除，则需要删除商户信息
                for (CartGoodsEntity cartGoods : cartGoodsList) {
                    Boolean checked = cartGoods.getChecked();
                    if (checked) {
                        deleteGoodsIdList.add(cartGoods.getId());
                    }else {
                        isDeleteMerchant = false;
                    }
                }
                if (isDeleteMerchant) {
                    deleteCartIdList.add(cart.getId());
                }
                //删除商品信息
                if (!CollectionUtils.isEmpty(deleteGoodsIdList)) {
                    cartGoodsService.deleteBatch(deleteGoodsIdList);
                }
            }
            //删除商家信息
            if (!CollectionUtils.isEmpty(deleteCartIdList)) {
                cartService.deleteBatch(deleteCartIdList);
            }
        } else if (deleteType == 2) { //清空购物车
            List<CartEntity> cartList = cartService.listAll(memberId);
            List<Long> deleteCartIdList = new ArrayList<>();
            for (CartEntity cart : cartList) {
                List<CartGoodsEntity> cartGoodsList = cart.getCartGoodsList();
                List<Long> deleteGoodsIdList = new ArrayList<>();
                for (CartGoodsEntity cartGoods : cartGoodsList) {
                    deleteGoodsIdList.add(cartGoods.getId());
                }
                deleteCartIdList.add(cart.getId());
                //删除商品信息
                if (!CollectionUtils.isEmpty(deleteGoodsIdList)) {
                    cartGoodsService.deleteBatch(deleteGoodsIdList);
                }
            }
            //删除商家信息
            if (!CollectionUtils.isEmpty(deleteCartIdList)) {
                cartService.deleteBatch(deleteCartIdList);
            }
        }
        return CommonResult.success();
    }

}
