package com.mall.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.admin.entity.PayTypeEntity;
import com.mall.common.base.utils.PageUtil;

import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 支付管理（支付方式）
 * @Date 2022/6/19 14:44
 * @Version 1.0
 */
public interface PayTypeService extends IService<PayTypeEntity> {

    PageUtil queryPage(Integer pageNum, Integer pageSize, String name, Boolean enable);

    List<PayTypeEntity> getPayTypeList(String name, Boolean enable);

    PayTypeEntity getPayTypeById(Long id);

    int savePayType(PayTypeEntity payType);

    int updatePayType(PayTypeEntity payType);

    void deleteBatch(List<Long> ids);

    int updateEnable(Long id, Boolean enable);
}
