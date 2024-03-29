package com.mall.seckill.api.feign;

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
 * @Date 2022/9/26 17:18
 * @Version 1.0
 */
@FeignClient(value = ServiceNameConstant.MALL_SECKILL,configuration = FeignConfig.class)
@RequestMapping("seckillConfig")
public interface FeignSeckillConfigService {

    /**
     * 所有单个秒杀配置
     */
    @GetMapping("getById")
    CommonResult getById(@RequestParam("seckillConfigId") Long seckillConfigId);
}
