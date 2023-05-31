package com.mall.cart.api.feign;

import com.mall.common.base.CommonResult;
import com.mall.common.base.config.FeignConfig;
import com.mall.common.base.constant.ServiceNameConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 购物车
 * @Date 2022/6/13 20:57
 * @Version 1.0
 */
@FeignClient(value = ServiceNameConstant.MALL_CART,configuration = FeignConfig.class)
@RequestMapping(value = "cart")
public interface FeignCartService {

    /**
     * 查询会员购物车信息
     * @return
     */
    @GetMapping("/list")
    CommonResult list(@RequestHeader("currentUserInfo") String currentUserInfo);

    /**
     * 删除购物车信息
     * @param deleteType 0：单个删除  1：选中删除 2：清空购物车
     * @param cartGoodsId 单个删除时，传递商品sku信息id
     * @param currentUserInfo 请求头携带用户信息
     * @return
     */
    @DeleteMapping("deleteCart")
    CommonResult deleteCart(@RequestParam("deleteType") Integer deleteType,
                            @RequestParam(value = "cartGoodsId", required = false) Long cartGoodsId,
                            @RequestHeader("currentUserInfo") String currentUserInfo);
}
