package com.mall.log.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.log.entity.RabbitMQSendLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2023/5/15 16:49
 * @Version 1.0
 */
@Mapper
public interface RabbitMQSendLogMapper extends BaseMapper<RabbitMQSendLogEntity> {
}
