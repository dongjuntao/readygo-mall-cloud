package com.mall.cart.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.cart.entity.RecipientInfoSelectedEntity;
import com.mall.cart.mapper.RecipientInfoSelectedMapper;
import com.mall.cart.service.RecipientInfoSelectedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author DongJunTao
 * @Description 购物车内已选择的收货信息
 * @Date 2022/5/28 17:28
 * @Version 1.0
 */
@Service("recipientInfoSelectedService")
public class RecipientInfoSelectedServiceImpl extends ServiceImpl<RecipientInfoSelectedMapper, RecipientInfoSelectedEntity>
        implements RecipientInfoSelectedService {

    @Autowired
    private RecipientInfoSelectedMapper recipientInfoSelectedMapper;


    @Override
    public int saveRecipientInfoSelected(RecipientInfoSelectedEntity recipientInfoSelected) {
        return baseMapper.insert(recipientInfoSelected);
    }

    @Override
    public int updateRecipientInfoSelected(RecipientInfoSelectedEntity recipientInfoSelected) {
        return baseMapper.updateById(recipientInfoSelected);
    }

    @Override
    public RecipientInfoSelectedEntity getRecipientInfoSelectedByMemberId(Long memberId) {
        QueryWrapper<RecipientInfoSelectedEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(memberId != null, "member_id", memberId);
        return baseMapper.selectOne(queryWrapper);
    }
}
