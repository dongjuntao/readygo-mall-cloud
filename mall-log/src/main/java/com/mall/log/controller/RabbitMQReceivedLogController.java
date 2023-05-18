package com.mall.log.controller;

import com.mall.common.base.CommonResult;
import com.mall.common.base.enums.ResultCodeEnum;
import com.mall.log.entity.RabbitMQReceivedLogEntity;
import com.mall.log.entity.RabbitMQSendLogEntity;
import com.mall.log.service.RabbitMQReceivedLogService;
import com.mall.log.service.RabbitMQSendLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2023/5/15 16:55
 * @Version 1.0
 */
@RestController
@RequestMapping("rabbit/received/log")
public class RabbitMQReceivedLogController {

    @Autowired
    private RabbitMQReceivedLogService rabbitMQReceivedLogService;

    private static int MAX_TRY_COUNT = 3; //最大重试次数

    /**
     * 新增日志
     * @param rabbitMQReceivedLog
     * @return
     */
    @PostMapping("save")
    public CommonResult save(@RequestBody RabbitMQReceivedLogEntity rabbitMQReceivedLog) {
        rabbitMQReceivedLog.setCreateTime(new Date());
        rabbitMQReceivedLog.setStatus(0); //刚开始默认待消费
        rabbitMQReceivedLog.setTryCount(0); //刚开始，重试次数为0，还未重试
        rabbitMQReceivedLogService.save(rabbitMQReceivedLog);
        return CommonResult.success();
    }

    /**
     * 更新日志
     * @param rabbitMQReceivedLog
     * @return
     */
    @PutMapping("update")
    public CommonResult update(@RequestBody RabbitMQReceivedLogEntity rabbitMQReceivedLog) {
        RabbitMQReceivedLogEntity log = rabbitMQReceivedLogService.getById(rabbitMQReceivedLog.getMessageId());
        if (log == null) {
            return CommonResult.fail(ResultCodeEnum.LOG_NOT_EXIST.getCode(), ResultCodeEnum.LOG_NOT_EXIST.getMessage());
        }
        //超过最大重试次数，
        if (log.getTryCount() == MAX_TRY_COUNT) {
            return CommonResult.fail(ResultCodeEnum.EXCEEDED_MAX_RETRY_COUNT.getCode(), ResultCodeEnum.EXCEEDED_MAX_RETRY_COUNT.getMessage());
        }
        rabbitMQReceivedLog.setTryCount(log.getTryCount()+1); //重试次数加1
        rabbitMQReceivedLog.setTryTime(new Date());
        rabbitMQReceivedLogService.saveOrUpdate(rabbitMQReceivedLog);
        return CommonResult.success();
    }
}
