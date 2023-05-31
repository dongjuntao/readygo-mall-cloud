package com.mall.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.admin.entity.ShippingInfoEntity;
import com.mall.common.base.utils.PageUtil;

import java.util.Map;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2021/8/31 22:12
 * @Version 1.0
 */
public interface ShippingInfoService extends IService<ShippingInfoEntity> {
    /**
     * 查询发货信息列表
     * @param pageNum
     * @param pageSize
     * @param name
     * @param adminUserId
     * @param mobile
     * @return
     */
    PageUtil queryPage(Integer pageNum, Integer pageSize, String name, Long adminUserId, String mobile);
    /**
     * 新增发货信息
     * @param shippingInfoEntity
     * @return
     */
    int saveShippingInfo(ShippingInfoEntity shippingInfoEntity);
    /**
     * 修改发货信息
     * @param shippingInfoEntity
     * @return
     */
    int updateShippingInfo(ShippingInfoEntity shippingInfoEntity);
    /**
     * 删除发货信息
     * @param id
     * @return
     */
    int deleteShippingInfo(Long id);

    /**
     * 根据id获取发货信息
     * @param id
     * @return
     */
    ShippingInfoEntity getShippingInfoById(Long id);

    void updateIsDefault(Long id, Boolean isDefault);

}
