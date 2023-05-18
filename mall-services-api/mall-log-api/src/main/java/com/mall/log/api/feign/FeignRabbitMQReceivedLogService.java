package com.mall.log.api.feign;

import com.mall.common.base.CommonResult;
import com.mall.common.base.config.FeignConfig;
import com.mall.common.base.constant.ServiceNameConstant;
import com.mall.log.api.dto.RabbitMQReceivedLogDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2022/4/16 20:19
 * @Version 1.0
 */
@FeignClient(value = ServiceNameConstant.MALL_LOG,configuration = FeignConfig.class,path = "rabbit/received/log")
public interface FeignRabbitMQReceivedLogService {

    /**
     * 新增日志
     * @param rabbitMQReceivedLogDTO
     * @return
     */
    @PostMapping("save")
    CommonResult save(@RequestBody RabbitMQReceivedLogDTO rabbitMQReceivedLogDTO);

    /**
     * 新增日志
     * @param rabbitMQReceivedLogDTO
     * @return
     */
    @PutMapping("update")
    CommonResult update(@RequestBody RabbitMQReceivedLogDTO rabbitMQReceivedLogDTO);
}
