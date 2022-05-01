package com.mall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.common.base.utils.PageUtil;
import com.mall.member.entity.FootprintEntity;

import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 会员足迹service
 * @Date 2022/4/28 20:52
 * @Version 1.0
 */
public interface FootprintService extends IService<FootprintEntity> {

    /**
     * 分页查询用户足迹
     * @param params
     * @return
     */
    PageUtil getByPage(Map<String, Object> params);

    /**
     * 根据参数查询足迹实体
     * @return
     */
    FootprintEntity getFootprintByParams(Map<String, Object> params);

    int deleteFootprint(Map<String, Object> params);

}
