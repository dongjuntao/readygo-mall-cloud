package com.mall.admin.api.feign;

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
 * @Date 2022/4/16 20:19
 * @Version 1.0
 */
@FeignClient(value = ServiceNameConstant.MALL_ADMIN,configuration = FeignConfig.class)
@RequestMapping(value = "logistics/region")
public interface FeignRegionService {

    /**
     * 根据regions查询省、市、区县信息（如：安徽省 淮南市 寿县）
     * @param regions
     * @return
     */
    @GetMapping("getRegionsNameByRegions")
    CommonResult getRegionsNameByRegions(@RequestParam("regions") String regions);
}
