package com.mall.admin.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.admin.entity.FreightTemplateEntity;
import com.mall.admin.entity.LogisticsCompanyEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Author DongJunTao
 * @Description 运费模板Mapper
 * @Date 2021/9/17 23:06
 * @Version 1.0
 */
@Mapper
public interface FreightTemplateMapper extends BaseMapper<FreightTemplateEntity> {

    IPage<FreightTemplateEntity> getFreightTemplateEntityByPage(
            @Param("page") Page<FreightTemplateEntity> page,
            @Param(Constants.WRAPPER) QueryWrapper<FreightTemplateEntity> wrapper,
            @Param("createBy") Long createBy);
}
