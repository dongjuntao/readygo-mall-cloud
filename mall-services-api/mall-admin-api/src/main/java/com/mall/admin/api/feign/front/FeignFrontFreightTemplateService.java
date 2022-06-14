package com.mall.admin.api.feign.front;

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
 * @Date 2022/6/8 21:27
 * @Version 1.0
 */
@FeignClient(value = ServiceNameConstant.MALL_ADMIN,configuration = FeignConfig.class)
@RequestMapping("front/store/freightTemplate")
public interface FeignFrontFreightTemplateService {

    /**
     * 根据主键id获取运费模板
     * @param id
     * @return
     */
    @GetMapping("getFreightTemplateById")
    CommonResult getShippingInfoById(@RequestParam long id);
}
