package com.mall.goods.api.front;

import com.mall.common.base.CommonResult;
import com.mall.common.base.config.FeignConfig;
import com.mall.common.base.constant.ServiceNameConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2022/1/6 13:23
 * @Version 1.0
 */
@FeignClient(value = ServiceNameConstant.MALL_GOODS,configuration = FeignConfig.class)
@RequestMapping(value = "front/goods")
public interface FeignGoodsService {

    /**
     * 通过ids获取商品列表
     * @param ids
     * @return
     */
    @GetMapping("/listByIds")
    CommonResult listByIds(@RequestParam Long[] ids);

    /**
     * 根据skuIds获取sku列表信息
     * @param ids
     * @return
     */
    @GetMapping("/getGoodsSkuList")
    CommonResult getGoodsSkuList(@RequestParam Long[] ids);
}
