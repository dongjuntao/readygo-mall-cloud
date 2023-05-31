package com.mall.admin.api.feign;

import com.mall.common.base.CommonResult;
import com.mall.common.base.config.FeignConfig;
import com.mall.common.base.constant.ServiceNameConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(value = ServiceNameConstant.MALL_ADMIN,configuration = FeignConfig.class)
@RequestMapping(value = "system/admin")
public interface FeignAdminUserService {

    @GetMapping("/getUserById")
    CommonResult getAdminUserById(@RequestParam long id);

    @GetMapping("/getUserByParams")
    CommonResult getUserByParams(@RequestParam(value = "userName", required = false) String userName,
                                 @RequestParam(value = "userType", required = false) Integer userType,
                                 @RequestParam(value = "id", required = false) Long id);
    /**
     * 所有用户列表（不分页）
     */
    @GetMapping("/listByIds")
    CommonResult listByIds(@RequestParam Long[] ids);
}
