package com.mall.admin.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.admin.entity.LogisticsCompanyEntity;
import com.mall.admin.entity.ShippingInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2021/9/7 21:18
 * @Version 1.0
 */
@Mapper
public interface LogisticsCompanyMapper extends BaseMapper<LogisticsCompanyEntity> {

    IPage<LogisticsCompanyEntity> getWithExpressSettingByPage(
            @Param("page") Page<LogisticsCompanyEntity> page,
            @Param(Constants.WRAPPER) QueryWrapper<LogisticsCompanyEntity> wrapper,
            @Param("adminUserId") Long adminUserId);

    /**
     * 物流公司，包含部分快递设置内容
     * @return
     */
    List<LogisticsCompanyEntity> getAllWithExpressSetting(@Param("name") String name,
                                                          @Param("abbreviation") String abbreviation,
                                                          @Param("code") String code,
                                                          @Param("enable") Boolean enable,
                                                          @Param("adminUserId") Long adminUserId);
}
