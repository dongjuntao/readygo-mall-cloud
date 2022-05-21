package com.mall.cart.controller;

import com.mall.admin.api.feign.front.FeignAdminUserService;
import com.mall.cart.dto.CartGoodsDTO;
import com.mall.cart.entity.CartEntity;
import com.mall.cart.entity.CartGoodsEntity;
import com.mall.cart.service.CartGoodsService;
import com.mall.cart.service.CartService;
import com.mall.cart.vo.CartGoodsVO;
import com.mall.cart.vo.CartMerchantVO;
import com.mall.cart.vo.CartVO;
import com.mall.common.base.CommonResult;
import com.mall.common.base.enums.ResultCodeEnum;
import com.mall.common.base.utils.CurrentUserContextUtil;
import com.mall.goods.api.front.FeignGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

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
        Long start = System.currentTimeMillis();
        params.put("memberId", CurrentUserContextUtil.getCurrentUserInfo().getUserId());
        List<CartEntity> cartList = cartService.listAll(params);
        Long[] merchantIds = new Long[cartList.size()]; //商家id集合
        Integer totalCount = 0; //总量
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
                    cartGoodsVO.setStock(Integer.parseInt(String.valueOf(skuMap.get("stock"))));
                    //“购买数量”, “是否被选中” 从购物车表中单独获取
                    List<CartGoodsEntity> cartGoodsList = cartList.get(i).getCartGoodsList();
                    Optional<CartGoodsEntity> optional = cartGoodsList.stream().filter(cartGoods->cartGoods.getGoodsSkuId().equals(Long.valueOf(String.valueOf(skuMap.get("id"))))).findFirst();
                    if(optional.isPresent()) {
                        cartGoodsVO.setId(optional.get().getId());
                        cartGoodsVO.setCount(optional.get().getCount());
                        cartGoodsVO.setChecked(optional.get().getChecked());
                        cartGoodsVO.setSubTotal(new BigDecimal(String.valueOf(skuMap.get("sellingPrice")))
                                .multiply(new BigDecimal(optional.get().getCount())));
                        if (optional.get().getChecked()) {
                            //选中时计算选中数量和价格
                            totalCount += cartGoodsVO.getCount();
                            totalPrice = totalPrice.add(new BigDecimal(String.valueOf(cartGoodsVO.getSellingPrice()))
                                    .multiply(new BigDecimal(cartGoodsVO.getCount())));
                        }
                    }else {
                        cartGoodsVO.setId(0l);
                        cartGoodsVO.setCount(1);
                        cartGoodsVO.setChecked(false);
                        cartGoodsVO.setSubTotal(new BigDecimal("0"));
                    }
                    cartGoodsVOList.add(cartGoodsVO);
                }
                cartMerchantVO.setCartGoodsList(cartGoodsVOList);
            }
            cartMerchantVOList.add(cartMerchantVO);
        }
        System.out.println("耗时----"+(System.currentTimeMillis() - start));
        CartVO cartVO = new CartVO();
        cartVO.setCartMerchantList(cartMerchantVOList); //购物车列表
        cartVO.setTotalCount(totalCount); //总数量
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
        if (type == 0) {
            Map<String,Object> params = new HashMap<>();
            params.put("memberId", CurrentUserContextUtil.getCurrentUserInfo().getUserId());
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
            CartEntity cart = cartService.getCart(CurrentUserContextUtil.getCurrentUserInfo().getUserId(), bizId);
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
}
