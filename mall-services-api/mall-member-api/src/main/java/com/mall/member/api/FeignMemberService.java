package com.mall.member.api;

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
 * @Description
 * @Date 2022/4/15 20:31
 * @Version 1.0
 */
@FeignClient(value = ServiceNameConstant.MALL_MEMBER,configuration = FeignConfig.class)
@RequestMapping(value = "member")
public interface FeignMemberService {

    /**
     * 根据用户名查看用户
     * @return
     */
    @GetMapping("getMemberByParams")
    CommonResult getMemberByParams(@RequestParam(value = "userName",required = false) String userName,
                                   @RequestParam(value = "id",required = false) Long id);

    @GetMapping("count")
    CommonResult count();
}
