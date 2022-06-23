package com.mall.admin.api.feign.front;

import com.mall.common.base.CommonResult;
import com.mall.common.base.config.FeignConfig;
import com.mall.common.base.constant.ServiceNameConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 支付类型
 * @Date 2022/6/20 22:18
 * @Version 1.0
 */
@FeignClient(value = ServiceNameConstant.MALL_ADMIN,configuration = FeignConfig.class)
@RequestMapping(value = "front/payType")
public interface FrontPayTypeService {

    /**
     * 查询支付方式（不分页）
     */
    @GetMapping("listAll")
    CommonResult listAll(@RequestParam Map<String, Object> params);
}
