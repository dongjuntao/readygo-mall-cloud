package com.mall.admin.api.feign;

import com.mall.base.config.FeignConfig;
import com.mall.base.constant.ServiceNameConstant;
import com.mall.base.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = ServiceNameConstant.MALL_ADMIN,configuration = FeignConfig.class)
@RequestMapping(value = "/user")
public interface AdminUserService {

    @GetMapping("/getAdminUserById")
    UserDTO getAdminUserById(@RequestParam long id);

    @GetMapping("/getAdminUserByUserName")
    UserDTO getAdminUserByUserName(@RequestParam String userName);
}
