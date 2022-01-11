package com.mall.seckill.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.seckill.entity.SeckillConfigEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Author DongJunTao
 * @Description 秒杀配置
 * @Date 2022/1/6 9:19
 * @Version 1.0
 */
@Mapper
public interface SeckillConfigMapper extends BaseMapper<SeckillConfigEntity> {
    /**
     * 分页查询秒杀列表
     * @param page
     * @param wrapper
     * @return
     */
    IPage<SeckillConfigEntity> queryPage(
            @Param("page") Page<SeckillConfigEntity> page,
            @Param(Constants.WRAPPER) QueryWrapper<SeckillConfigEntity> wrapper);

    SeckillConfigEntity getById(Long id);
}
