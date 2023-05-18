package com.mall.log.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.log.entity.RabbitMQReceivedLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2023/5/15 16:49
 * @Version 1.0
 */
@Mapper
public interface RabbitMQReceivedLogMapper extends BaseMapper<RabbitMQReceivedLogEntity> {
}
