package com.mall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.member.entity.RecipientInfoEntity;

import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 收货信息service
 * @Date 2022/4/16 18:12
 * @Version 1.0
 */
public interface RecipientInfoService extends IService<RecipientInfoEntity> {

    /**
     * 查询所有收货信息
     * @param params
     * @return
     */
    List<RecipientInfoEntity> listAll(Map<String, Object> params);
    /**
     * 新增发货信息
     * @param recipientInfoEntity
     * @return
     */
    int saveRecipientInfo(RecipientInfoEntity recipientInfoEntity);
    /**
     * 修改发货信息
     * @param recipientInfoEntity
     * @return
     */
    int updateRecipientInfo(RecipientInfoEntity recipientInfoEntity);
    /**
     * 删除发货信息
     * @param id
     * @return
     */
    int deleteRecipientInfo(Long id);

    /**
     * 根据id获取发货信息
     * @param id
     * @return
     */
    RecipientInfoEntity getRecipientInfoById(Long id);

    void updateIsDefault(Long id, Boolean isDefault);
}
