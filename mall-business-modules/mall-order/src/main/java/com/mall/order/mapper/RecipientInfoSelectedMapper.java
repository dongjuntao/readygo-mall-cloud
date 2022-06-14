package com.mall.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.order.entity.RecipientInfoSelectedEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author DongJunTao
 * @Description 购物车内已选择的收货信息
 * @Date 2022/5/28 17:26
 * @Version 1.0
 */
@Mapper
public interface RecipientInfoSelectedMapper extends BaseMapper<RecipientInfoSelectedEntity> {
}
