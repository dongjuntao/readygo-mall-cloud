package com.mall.member.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.member.entity.RecipientInfoEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 收货信息mapper
 * @Date 2022/4/16 18:10
 * @Version 1.0
 */
@Mapper
public interface RecipientInfoMapper extends BaseMapper<RecipientInfoEntity> {
}
