package com.mall.auth.openfeign;

import com.mall.base.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "mall-admin")
public interface AdminUserService {

    @GetMapping("/user/getAdminUserById")
    UserDTO getAdminUserById(@RequestParam long id);

    @GetMapping("/user/getAdminUserByUserName")
    UserDTO getAdminUserByUserName(@RequestParam String userName);
}
