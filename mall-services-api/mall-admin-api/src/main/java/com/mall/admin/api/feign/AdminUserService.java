package com.mall.admin.api.feign;

import com.mall.base.CommonResult;
import com.mall.base.config.FeignConfig;
import com.mall.base.constant.ServiceNameConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = ServiceNameConstant.MALL_ADMIN,configuration = FeignConfig.class)
@RequestMapping(value = "system/admin")
public interface AdminUserService {

    @GetMapping("/getUserById")
    CommonResult getAdminUserById(@RequestParam long id);

    @GetMapping("/getUserByUserName")
    CommonResult getAdminUserByUserName(@RequestParam String userName);
}
