package com.mall.cart.controller;

import com.mall.admin.api.feign.front.FeignAdminUserService;
import com.mall.cart.dto.CartGoodsDTO;
import com.mall.cart.entity.CartCouponSelectedEntity;
import com.mall.cart.entity.CartEntity;
import com.mall.cart.entity.CartGoodsEntity;
import com.mall.cart.service.CartCouponSelectedService;
import com.mall.cart.service.CartGoodsService;
import com.mall.cart.service.CartService;
import com.mall.cart.vo.*;
import com.mall.common.base.CommonResult;
import com.mall.common.base.enums.ResultCodeEnum;
import com.mall.common.base.utils.CurrentUserContextUtil;
import com.mall.common.base.utils.PageUtil;
import com.mall.coupon.api.feign.front.FeignFrontCouponService;
import com.mall.goods.api.front.FeignGoodsService;
import com.mall.member.api.FeignCouponReceivedService;
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
    private FeignGoodsService feignGoodsService;

    @Autowired
    private CartCouponSelectedService cartCouponSelectedService;

    @Autowired
    private FeignCouponReceivedService feignCouponReceivedService;

    @Autowired
    private FeignFrontCouponService feignFrontCouponService;

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
                CommonResult skuResult = feignGoodsService.getGoodsSkuList(skuIds);
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
            cartGoods.setChecked(checked);
            cartGoodsList.add(cartGoods);
            cartGoodsService.updateBatch(cartGoodsList);
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

    /**
     * 获取结算页信息 【商品信息】
     * @return
     */
    @GetMapping("/payInfo/goods")
    public CommonResult goods(@RequestParam Map<String, Object> params) {
        params.put("memberId", CurrentUserContextUtil.getCurrentUserInfo().getUserId());
        List<CartEntity> cartList = cartService.listAll(params);
        Long[] merchantIds = new Long[cartList.size()]; //商家id集合
        Integer totalCount = 0;
        BigDecimal totalPrice = new BigDecimal("0"); //总价
        List<PayMerchantVO> payMerchantVOList = new ArrayList<>(); //封装购物车信息
        Map<String, Object> skuListMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(cartList)) {
            for (int i=0; i<cartList.size(); i++) {
                merchantIds[i] = cartList.get(i).getMerchantId();
                List<CartGoodsEntity> cartGoodsList = cartList.get(i).getCartGoodsList();
                Long[] skuIds = new Long[cartGoodsList.size()];
                for (int j=0; j<cartGoodsList.size(); j++) {
                    skuIds[j] = cartList.get(i).getCartGoodsList().get(j).getGoodsSkuId();
                }
                CommonResult skuResult = feignGoodsService.getGoodsSkuList(skuIds);
                skuListMap.put(merchantIds[i].toString(), skuResult.getData());
            }
        }
        if (merchantIds == null || merchantIds.length==0) {
            return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), payMerchantVOList);
        }
        //查询所有商家信息
        CommonResult merchantResult = feignAdminUserService.listByIds(merchantIds);
        if (merchantResult == null || !"200".equals(merchantResult.getCode())) {
            return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), payMerchantVOList);
        }
        List merchantList = (List) merchantResult.getData();
        for(int i=0; i<merchantList.size(); i++) {
            PayMerchantVO payMerchantVO = new PayMerchantVO();
            Map<String,Object> map = (Map)merchantList.get(i);
            payMerchantVO.setMerchantId(Long.valueOf(map.get("id").toString())); //商家id
            payMerchantVO.setMerchantName(String.valueOf(map.get("name"))); //商家名称
            List skuList = (List)skuListMap.get(map.get("id").toString());
            if (!CollectionUtils.isEmpty(skuList)) {
                List<PayGoodsVO> payGoodsVOList = new ArrayList<>();
                Boolean merchantSelected = false; //该商家有商品时才记录
                for (int j=0; j<skuList.size(); j++) {
                    Map<String,Object> skuMap = (Map)skuList.get(j);
                    //“购买数量”, “是否被选中” 从购物车表中单独获取
                    List<CartGoodsEntity> cartGoodsList = cartList.stream()
                            .filter(cartEntity -> cartEntity.getMerchantId().equals(payMerchantVO.getMerchantId()))
                            .collect(Collectors.toList()).stream().findFirst().get().getCartGoodsList();
                    Optional<CartGoodsEntity> optional = cartGoodsList.stream().filter(cartGoods->cartGoods.getGoodsSkuId()
                            .equals(Long.valueOf(String.valueOf(skuMap.get("id"))))).findFirst();
                    PayGoodsVO payGoodsVO = new PayGoodsVO();
                    if(optional.isPresent()) {
                        //挑选出选中的记录
                        if (optional.get().getChecked()) {
                            merchantSelected = true;
                            payGoodsVO.setName(String.valueOf(skuMap.get("name"))); //商品名称
                            payGoodsVO.setImage(String.valueOf(skuMap.get("image"))); //商品图片
                            payGoodsVO.setSellingPrice(new BigDecimal(String.valueOf(skuMap.get("sellingPrice")))); //销售价格
                            payGoodsVO.setCount(optional.get().getCount());
                            //选中时计算选中数量和价格
                            totalPrice = totalPrice.add(new BigDecimal(String.valueOf(payGoodsVO.getSellingPrice()))
                                    .multiply(new BigDecimal(payGoodsVO.getCount())));
                            payGoodsVO.setId(optional.get().getId());
                            payGoodsVO.setGoodsId(optional.get().getGoodsId());
                            payGoodsVO.setGoodsSkuId(optional.get().getGoodsSkuId());
                            payGoodsVO.setSubTotal(new BigDecimal(String.valueOf(skuMap.get("sellingPrice")))
                                    .multiply(new BigDecimal(optional.get().getCount())));
                            totalCount += payGoodsVO.getCount();
                            payGoodsVOList.add(payGoodsVO);
                        }
                    }
                }
                payMerchantVO.setPayGoodsList(payGoodsVOList);
                //商家有商品被选中时，才记录该商家
                if (merchantSelected) {
                    payMerchantVOList.add(payMerchantVO);
                }
            }
        }
        PayVO payVO = new PayVO();
        payVO.setPayMerchantList(payMerchantVOList); //购物车列表
        payVO.setTotalCount(totalCount);
        payVO.setTotalPrice(totalPrice); //总价格
        payVO.setFinalPrice(new BigDecimal("0"));
        payVO.setDiscountPrice(new BigDecimal("0"));
        //查询使用使用优惠券
        List<CartCouponSelectedEntity> cartCouponSelectedList
                = cartCouponSelectedService.getSelected(CurrentUserContextUtil.getCurrentUserInfo().getUserId());
        if (!CollectionUtils.isEmpty(cartCouponSelectedList)) { //使用优惠券
            Long receivedCouponId = cartCouponSelectedList.get(0).getReceivedCouponId(); //我的优惠券信息
            CommonResult receivedCouponResult = feignCouponReceivedService.getById(receivedCouponId);
            if (receivedCouponResult == null || !"200".equals(receivedCouponResult.getCode())) {
                return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), payVO);
            }
            Map<String, Object> receivedCouponMap = (Map<String, Object>) receivedCouponResult.getData();
            Long couponId = Long.valueOf(String.valueOf(receivedCouponMap.get("couponId")));
            CommonResult couponResult = feignFrontCouponService.getById(couponId);
            if (couponResult == null || !"200".equals(couponResult.getCode())) {
                return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), payVO);
            }
            Map<String, Object> couponMap = (Map<String, Object>) couponResult.getData();
            Integer type = Integer.valueOf(String.valueOf(couponMap.get("type")));
            if (type == 0) { //满减券
                payVO.setDiscountPrice(new BigDecimal(String.valueOf(couponMap.get("discountAmount"))));
            } else if (type == 1) { //满折券
                payVO.setDiscountPrice(totalPrice.subtract(new BigDecimal(String.valueOf(couponMap.get("discountAmount"))).multiply(totalPrice).divide(new BigDecimal("10"))));
            }
            payVO.setFinalPrice(totalPrice.subtract(payVO.getDiscountPrice()));
        }
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), payVO);
    }


}
