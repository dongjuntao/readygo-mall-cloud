package com.mall.admin.api.feign;

import com.mall.base.constant.ServiceNameConstant;
import com.mall.base.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = ServiceNameConstant.MALL_ADMIN)
public interface AdminUserService {

    @GetMapping("/user/getAdminUserById")
    UserDTO getAdminUserById(@RequestParam long id);

    @GetMapping("/user/getAdminUserByUserName")
    UserDTO getAdminUserByUserName(@RequestParam String userName);
}
