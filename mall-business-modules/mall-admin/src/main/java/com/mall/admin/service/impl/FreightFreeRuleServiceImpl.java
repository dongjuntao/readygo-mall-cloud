package com.mall.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.admin.entity.FreightFreeRuleEntity;
import com.mall.admin.mapper.FreightFreeRuleMapper;
import com.mall.admin.service.FreightFreeRuleService;
import org.springframework.stereotype.Service;

/**
 * @Author DongJunTao
 * @Description 包邮规则service实现
 * @Date 2021/9/17 23:50
 * @Version 1.0
 */
@Service("freightFreeRuleService")
public class FreightFreeRuleServiceImpl extends ServiceImpl<FreightFreeRuleMapper, FreightFreeRuleEntity>
        implements FreightFreeRuleService {
}
