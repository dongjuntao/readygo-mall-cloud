package com.mall.cart.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.admin.api.feign.front.FeignAdminUserService;
import com.mall.cart.constant.RedisExpiresTimeConstant;
import com.mall.cart.constant.RedisKeyConstant;
import com.mall.cart.dto.CartGoodsDTO;
import com.mall.cart.entity.CartEntity;
import com.mall.cart.entity.CartGoodsEntity;
import com.mall.cart.mapper.CartGoodsMapper;
import com.mall.cart.mapper.CartMapper;
import com.mall.cart.service.CartService;
import com.mall.cart.vo.CartGoodsVO;
import com.mall.cart.vo.CartMerchantVO;
import com.mall.cart.vo.CartVO;
import com.mall.common.base.CommonResult;
import com.mall.common.base.enums.ResultCodeEnum;
import com.mall.common.seata.util.RedisUtil;
import com.mall.goods.api.FeignFrontGoodsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    private FeignAdminUserService feignAdminUserService;

    @Autowired
    private FeignFrontGoodsService feignFrontGoodsService;

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
            cartGoods.setChecked(true);//设置默认不选中
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
    public List<CartEntity> listAll(Long memberId) {
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

    @Override
    public CartVO getCartInfo(Long memberId) {
        List<CartEntity> cartList = listAll(memberId);
        Long[] merchantIds = new Long[cartList.size()]; //商家id集合
        Integer totalCount = 0; //总量
        Integer checkedTotalCount = 0; //选中的总量
        BigDecimal totalPrice = BigDecimal.ZERO; //总价
        List<CartMerchantVO> cartMerchantVOList = new ArrayList<>(); //封装购物车信息

        CartVO cartVO = new CartVO();
        cartVO.setCartMerchantList(cartMerchantVOList); //购物车列表
        cartVO.setTotalCount(totalCount);
        cartVO.setCheckedTotalCount(checkedTotalCount); //总数量
        cartVO.setTotalPrice(totalPrice); //总价格

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
            return cartVO;
        }
        //查询所有商家信息
        CommonResult merchantResult = feignAdminUserService.listByIds(merchantIds);
        if (merchantResult == null || !"200".equals(merchantResult.getCode())) {
            return cartVO;
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
        cartVO.setCartMerchantList(cartMerchantVOList); //购物车列表
        cartVO.setTotalCount(totalCount);
        cartVO.setCheckedTotalCount(checkedTotalCount); //总数量
        cartVO.setTotalPrice(totalPrice); //总价格
        return cartVO;
    }
}
