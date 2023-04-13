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

    @Autowired
    private FeignAdminUserService feignAdminUserService;

    @Autowired
    private FeignFrontGoodsService feignFrontGoodsService;

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
    public CommonResult list(@RequestParam Map<String, Object> params) {
        params.put("memberId", CurrentUserContextUtil.getCurrentUserInfo().getUserId());
        List<CartEntity> cartList = cartService.listAll(params);
        Long[] merchantIds = new Long[cartList.size()]; //商家id集合
        Integer totalCount = 0; //总量
        Integer checkedTotalCount = 0; //选中的总量
        BigDecimal totalPrice = new BigDecimal("0"); //总价
        List<CartMerchantVO> cartMerchantVOList = new ArrayList<>(); //封装购物车信息
        Map<String, Object> skuListMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(cartList)) {
            for (int i=0; i<cartList.size(); i++) {
                merchantIds[i] = cartList.get(i).getMerchantId();
                List<CartGoodsEntity> cartGoodsList = cartList.get(i).getCartGoodsList();
                Long[] skuIds = new Long[cartGoodsList.size()];
                for (int j=0; j<cartGoodsList.size(); j++) {
                    skuIds[j] = cartList.get(i).getCartGoodsList().get(j).getGoodsSkuId();
                }
                CommonResult skuResult = feignFrontGoodsService.getGoodsSkuList(skuIds);
                skuListMap.put(merchantIds[i].toString(), skuResult.getData());
            }
        }
        if (merchantIds == null || merchantIds.length==0) {
            return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), cartMerchantVOList);
        }
        //查询所有商家信息
        CommonResult merchantResult = feignAdminUserService.listByIds(merchantIds);
        if (merchantResult == null || !"200".equals(merchantResult.getCode())) {
            return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), cartMerchantVOList);
        }
        List merchantList = (List) merchantResult.getData();
        for(int i=0; i<merchantList.size(); i++) {
            CartMerchantVO cartMerchantVO = new CartMerchantVO();
            Map<String,Object> map = (Map)merchantList.get(i);
            cartMerchantVO.setMerchantId(Long.valueOf(map.get("id").toString())); //商家id
            cartMerchantVO.setMerchantName(String.valueOf(map.get("name"))); //商家名称
            List skuList = (List)skuListMap.get(map.get("id").toString());
            if (!CollectionUtils.isEmpty(skuList)){
                List<CartGoodsVO> cartGoodsVOList = new ArrayList<>();
                for (int j=0; j<skuList.size(); j++) {
                    Map<String,Object> skuMap = (Map)skuList.get(j);
                    CartGoodsVO cartGoodsVO = new CartGoodsVO();
                    cartGoodsVO.setName(String.valueOf(skuMap.get("name"))); //商品名称
                    cartGoodsVO.setImage(String.valueOf(skuMap.get("image"))); //商品图片
                    cartGoodsVO.setSellingPrice(new BigDecimal(String.valueOf(skuMap.get("sellingPrice")))); //销售价格
                    cartGoodsVO.setStock(Integer.parseInt(String.valueOf(skuMap.get("stock"))));//商品库存
                    //“购买数量”, “是否被选中” 从购物车表中单独获取
                    List<CartGoodsEntity> cartGoodsList = cartList.stream()
                            .filter(cartEntity -> cartEntity.getMerchantId().equals(cartMerchantVO.getMerchantId()))
                            .collect(Collectors.toList()).stream().findFirst().get().getCartGoodsList();
                    Optional<CartGoodsEntity> optional = cartGoodsList.stream().filter(cartGoods->cartGoods.getGoodsSkuId()
                            .equals(Long.valueOf(String.valueOf(skuMap.get("id"))))).findFirst();
                    if(optional.isPresent()) {
                        cartGoodsVO.setId(optional.get().getId());
                        cartGoodsVO.setGoodsId(optional.get().getGoodsId());
                        cartGoodsVO.setGoodsSkuId(optional.get().getGoodsSkuId());
                        cartGoodsVO.setCount(optional.get().getCount());
                        cartGoodsVO.setChecked(optional.get().getChecked());
                        cartGoodsVO.setSubTotal(new BigDecimal(String.valueOf(skuMap.get("sellingPrice")))
                                .multiply(new BigDecimal(optional.get().getCount())));
                        if (optional.get().getChecked()) {
                            //选中时计算选中数量和价格
                            checkedTotalCount += cartGoodsVO.getCount();
                            totalPrice = totalPrice.add(new BigDecimal(String.valueOf(cartGoodsVO.getSellingPrice()))
                                    .multiply(new BigDecimal(cartGoodsVO.getCount())));
                        }
                    }else {
                        cartGoodsVO.setId(0l);
                        cartGoodsVO.setCount(1);
                        cartGoodsVO.setChecked(false);
                        cartGoodsVO.setSubTotal(new BigDecimal("0"));
                    }
                    totalCount += cartGoodsVO.getCount();
                    cartGoodsVOList.add(cartGoodsVO);
                }
                cartMerchantVO.setCartGoodsList(cartGoodsVOList);
            }
            cartMerchantVOList.add(cartMerchantVO);
        }
        CartVO cartVO = new CartVO();
        cartVO.setCartMerchantList(cartMerchantVOList); //购物车列表
        cartVO.setTotalCount(totalCount);
        cartVO.setCheckedTotalCount(checkedTotalCount); //总数量
        cartVO.setTotalPrice(totalPrice); //总价格
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), cartVO);
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
        Map<String,Object> params = new HashMap<>();
        params.put("memberId", CurrentUserContextUtil.getCurrentUserInfo().getUserId());
        Long memberId = CurrentUserContextUtil.getCurrentUserInfo().getUserId();
        if (type == 0) {
            List<CartEntity> cartList = cartService.listAll(params);
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
            Map<String,Object> params = new HashMap<>();
            params.put("memberId", memberId);
            List<CartEntity> cartList = cartService.listAll(params);
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
            Map<String,Object> params = new HashMap<>();
            params.put("memberId", memberId);
            List<CartEntity> cartList = cartService.listAll(params);
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
