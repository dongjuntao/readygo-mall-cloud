package com.mall.member.api;

import com.mall.common.base.CommonResult;
import com.mall.common.base.config.FeignConfig;
import com.mall.common.base.constant.ServiceNameConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 收货人信息
 * @Date 2022/5/28 16:50
 * @Version 1.0
 */
@FeignClient(value = ServiceNameConstant.MALL_MEMBER,configuration = FeignConfig.class)
@RequestMapping(value = "recipientInfo")
public interface FeignRecipientInfoService {

    @GetMapping("list")
    CommonResult getRecipientInfoList(@RequestHeader("currentUserInfo") String currentUserInfo);
}
