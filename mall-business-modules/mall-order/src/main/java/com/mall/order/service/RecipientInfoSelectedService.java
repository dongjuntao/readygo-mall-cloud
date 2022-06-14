package com.mall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.order.entity.RecipientInfoSelectedEntity;

/**
 * @Author DongJunTao
 * @Description 购物车内已选择的收货信息
 * @Date 2022/5/28 17:28
 * @Version 1.0
 */
public interface RecipientInfoSelectedService extends IService<RecipientInfoSelectedEntity> {

    int saveRecipientInfoSelected(RecipientInfoSelectedEntity recipientInfoSelected);

    int updateRecipientInfoSelected(RecipientInfoSelectedEntity recipientInfoSelected);

    RecipientInfoSelectedEntity getRecipientInfoSelectedByMemberId(Long memberId);
}
