package com.mall.log.api.feign;

import com.mall.common.base.CommonResult;
import com.mall.common.base.config.FeignConfig;
import com.mall.common.base.constant.ServiceNameConstant;
import com.mall.log.api.dto.RabbitMQSendLogDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2022/4/16 20:19
 * @Version 1.0
 */
@FeignClient(value = ServiceNameConstant.MALL_LOG,configuration = FeignConfig.class,path = "rabbit/send/log")
public interface FeignRabbitMQSendLogService {

    /**
     * 新增日志
     * @param rabbitMQSendLogDTO
     * @return
     */
    @PostMapping("save")
    CommonResult save(@RequestBody RabbitMQSendLogDTO rabbitMQSendLogDTO);

    /**
     * 新增日志
     * @param rabbitMQSendLogDTO
     * @return
     */
    @PutMapping("update")
    CommonResult update(@RequestBody RabbitMQSendLogDTO rabbitMQSendLogDTO);
}
