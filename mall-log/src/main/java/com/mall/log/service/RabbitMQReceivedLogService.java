package com.mall.log.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.log.entity.RabbitMQReceivedLogEntity;
import com.mall.log.entity.RabbitMQSendLogEntity;

/**
 * @Author DongJunTao
 * @Description rabbitmq 发送消息日志
 * @Date 2023/5/15 16:50
 * @Version 1.0
 */
public interface RabbitMQReceivedLogService extends IService<RabbitMQReceivedLogEntity> {
}
