package com.mall.seckill.api.feign;

import com.mall.common.base.CommonResult;
import com.mall.common.base.config.FeignConfig;
import com.mall.common.base.constant.ServiceNameConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2022/9/15 11:15
 * @Version 1.0
 */
@FeignClient(value = ServiceNameConstant.MALL_SECKILL,configuration = FeignConfig.class)
@RequestMapping("seckillGoods")
public interface FeignSeckillGoodsService {

    /**
     * 获取当天所有批次的商品
     */
    @GetMapping("/batch")
    CommonResult seckillGoodsBatch(@RequestParam(value = "dateTime",required = false) String dateTime) throws ParseException;
}
