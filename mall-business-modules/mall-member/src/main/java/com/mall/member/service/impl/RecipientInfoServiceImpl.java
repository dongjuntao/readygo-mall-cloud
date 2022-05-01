package com.mall.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.common.base.utils.CurrentUserContextUtil;
import com.mall.member.entity.RecipientInfoEntity;
import com.mall.member.mapper.RecipientInfoMapper;
import com.mall.member.service.RecipientInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 收货信息serviceImpl
 * @Date 2022/4/16 18:12
 * @Version 1.0
 */
@Service("shippingInfoService")
public class RecipientInfoServiceImpl extends ServiceImpl<RecipientInfoMapper, RecipientInfoEntity> implements RecipientInfoService {

    /**
     * 查询所有收货信息
     * @param params
     * @return
     */
    @Override
    public List<RecipientInfoEntity> listAll(Map<String, Object> params) {
        Long memberId = CurrentUserContextUtil.getCurrentUserInfo().getUserId(); //当前登录人id
        QueryWrapper<RecipientInfoEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(memberId != null, "member_id", memberId);
        return this.baseMapper.selectList(queryWrapper);
    }

    /**
     * 新增发货信息
     * @param recipientInfoEntity
     * @return
     */
    @Override
    @Transactional
    public int saveRecipientInfo(RecipientInfoEntity recipientInfoEntity) {
        //是否设为默认，如果为否，无需处理，如果为是，需要把原先默认的改为非默认
        if (recipientInfoEntity.getIsDefault()) {
            //查询该会员下所有的发货地址，把原先默认的改为非默认
            Long memberId = recipientInfoEntity.getMemberId();
            RecipientInfoEntity infoEntity = this.getOne(new QueryWrapper<RecipientInfoEntity>()
                    .eq("member_id", memberId).eq("is_default", true));
            if (infoEntity != null){
                infoEntity.setIsDefault(false);
                baseMapper.updateById(infoEntity);
            }
        }
        return baseMapper.insert(recipientInfoEntity);
    }
    /**
     * 修改发货信息
     * @param recipientInfoEntity
     * @return
     */
    @Override
    public int updateRecipientInfo(RecipientInfoEntity recipientInfoEntity) {
        //是否设为默认，如果为否，无需处理，如果为是，需要把原先默认的改为非默认
        if (recipientInfoEntity.getIsDefault()) {
            //查询该会员下所有的发货地址，把原先默认的改为非默认
            Long memberId = recipientInfoEntity.getMemberId();
            RecipientInfoEntity infoEntity = this.getOne(new QueryWrapper<RecipientInfoEntity>()
                    .eq("member_id", memberId).eq("is_default", true));
            if (infoEntity != null){
                infoEntity.setIsDefault(false);
                baseMapper.updateById(infoEntity);
            }
        }
        return baseMapper.updateById(recipientInfoEntity);
    }

    /**
     * 删除发货信息
     * @param id
     * @return
     */
    @Override
    public int deleteRecipientInfo(Long id) {
        return baseMapper.deleteById(id);
    }

    @Override
    public RecipientInfoEntity getRecipientInfoById(Long id) {
        return baseMapper.selectById(id);
    }

    /**
     * 设为默认 / 取消默认
     * @param id
     * @param isDefault true:设为默认 false:取消默认
     */
    @Override
    @Transactional
    public void updateIsDefault(Long id, Boolean isDefault) {
        RecipientInfoEntity recipientInfoEntity = baseMapper.selectById(id);
        if (!isDefault){
            recipientInfoEntity.setIsDefault(false);
            this.updateById(recipientInfoEntity);
        }else {
            List<RecipientInfoEntity> updateList = new ArrayList<>();
            recipientInfoEntity.setIsDefault(true);
            updateList.add(recipientInfoEntity);
            //查询该会员下所有的发货地址，把原先默认的改为非默认
            Long memberId = recipientInfoEntity.getMemberId();
            RecipientInfoEntity infoEntity = this.getOne(new QueryWrapper<RecipientInfoEntity>()
                    .eq("member_id", memberId).eq("is_default", true));
            if (infoEntity != null){
                infoEntity.setIsDefault(false);
                updateList.add(infoEntity);
            }
            this.saveOrUpdateBatch(updateList);
        }

    }
}
