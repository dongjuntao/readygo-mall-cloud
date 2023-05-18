package com.mall.log.controller;

import com.mall.common.base.CommonResult;
import com.mall.common.base.enums.ResultCodeEnum;
import com.mall.log.entity.RabbitMQSendLogEntity;
import com.mall.log.service.RabbitMQSendLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2023/5/15 16:55
 * @Version 1.0
 */
@RestController
@RequestMapping("rabbit/send/log")
public class RabbitMQSendLogController {

    @Autowired
    private RabbitMQSendLogService rabbitMQSendLogService;

    private static int MAX_TRY_COUNT = 3; //最大重试次数

    /**
     * 新增日志
     * @param rabbitMQSendLog
     * @return
     */
    @PostMapping("save")
    public CommonResult save(@RequestBody RabbitMQSendLogEntity rabbitMQSendLog) {
        rabbitMQSendLog.setCreateTime(new Date());
        rabbitMQSendLog.setStatus(0); //刚开始默认发送中
        rabbitMQSendLog.setTryCount(0); //刚开始，重试次数为0，还未重试
        rabbitMQSendLogService.save(rabbitMQSendLog);
        return CommonResult.success();
    }

    /**
     * 更新日志
     * @param rabbitMQSendLog
     * @return
     */
    @PutMapping("update")
    public CommonResult update(@RequestBody RabbitMQSendLogEntity rabbitMQSendLog) {
        RabbitMQSendLogEntity log = rabbitMQSendLogService.getById(rabbitMQSendLog.getMessageId());
        if (log == null) {
            return CommonResult.fail(ResultCodeEnum.LOG_NOT_EXIST.getCode(), ResultCodeEnum.LOG_NOT_EXIST.getMessage());
        }
        //超过最大重试次数，
        if (log.getTryCount() == MAX_TRY_COUNT) {
            return CommonResult.fail(ResultCodeEnum.EXCEEDED_MAX_RETRY_COUNT.getCode(), ResultCodeEnum.EXCEEDED_MAX_RETRY_COUNT.getMessage());
        }
        rabbitMQSendLog.setTryCount(log.getTryCount()+1); //重试次数加1
        rabbitMQSendLog.setTryTime(new Date());
        rabbitMQSendLogService.saveOrUpdate(rabbitMQSendLog);
        return CommonResult.success();
    }
}
