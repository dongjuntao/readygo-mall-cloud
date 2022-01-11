package com.mall.goods.api;

import com.mall.common.base.CommonResult;
import com.mall.common.base.config.FeignConfig;
import com.mall.common.base.constant.ServiceNameConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2022/1/6 13:23
 * @Version 1.0
 */
@FeignClient(value = ServiceNameConstant.MALL_GOODS,configuration = FeignConfig.class)
@RequestMapping(value = "goods")
public interface GoodsService {

    /**
     * 通过id获取商品信息
     * @param id
     * @return
     */
    @GetMapping("/getGoodsById")
    CommonResult info(@RequestParam("id") Long id);
}
