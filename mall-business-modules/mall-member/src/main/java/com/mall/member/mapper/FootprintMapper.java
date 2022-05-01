package com.mall.member.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.member.entity.FootprintEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Author DongJunTao
 * @Description 会员足迹mapper
 * @Date 2022/4/28 20:50
 * @Version 1.0
 */
@Mapper
public interface FootprintMapper extends BaseMapper<FootprintEntity> {

    IPage<FootprintEntity> getFootprintEntityByPage(
            @Param("page") Page<FootprintEntity> page,
            @Param(Constants.WRAPPER) QueryWrapper<FootprintEntity> wrapper);
}
