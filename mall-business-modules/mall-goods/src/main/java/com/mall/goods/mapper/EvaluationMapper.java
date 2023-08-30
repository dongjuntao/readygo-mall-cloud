package com.mall.goods.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.goods.entity.EvaluationEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface EvaluationMapper extends BaseMapper<EvaluationEntity> {

    IPage<EvaluationEntity> queryPage(
            @Param("page") Page<EvaluationEntity> page,
            @Param(Constants.WRAPPER) QueryWrapper<EvaluationEntity> wrapper);
}
