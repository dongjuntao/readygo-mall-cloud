package com.mall.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.admin.entity.PayTypeEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author DongJunTao
 * @Description 支付管理（支付方式）
 * @Date 2022/6/19 14:44
 * @Version 1.0
 */
@Mapper
public interface PayTypeMapper extends BaseMapper<PayTypeEntity> {
}
