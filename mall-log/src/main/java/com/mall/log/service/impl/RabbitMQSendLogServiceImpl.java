package com.mall.log.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.log.entity.RabbitMQSendLogEntity;
import com.mall.log.mapper.RabbitMQSendLogMapper;
import com.mall.log.service.RabbitMQSendLogService;
import org.springframework.stereotype.Service;

/**
 * @Author DongJunTao
 * @Description rabbitmq 发送消息日志
 * @Date 2023/5/15 16:51
 * @Version 1.0
 */
@Service
public class RabbitMQSendLogServiceImpl extends ServiceImpl<RabbitMQSendLogMapper, RabbitMQSendLogEntity>
        implements RabbitMQSendLogService {
}
