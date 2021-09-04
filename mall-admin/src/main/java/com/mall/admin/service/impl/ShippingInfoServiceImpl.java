package com.mall.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.admin.entity.AdminUserEntity;
import com.mall.admin.entity.ShippingInfoEntity;
import com.mall.admin.mapper.ShippingInfoMapper;
import com.mall.admin.service.ShippingInfoService;
import com.mall.common.util.PageBuilder;
import com.mall.common.util.PageUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2021/8/31 22:14
 * @Version 1.0
 */
@Service("shippingInfoService")
public class ShippingInfoServiceImpl extends ServiceImpl<ShippingInfoMapper, ShippingInfoEntity>
        implements ShippingInfoService {

    @Override
    public PageUtil queryPage(Map<String, Object> params) {

//        String name = params.get("name") == null ? null : params.get("name").toString();
//        IPage<ShippingInfoEntity> page = this.page(
//                new PageBuilder<ShippingInfoEntity>().getPage(params),
//                new QueryWrapper<ShippingInfoEntity>()
//                        .like(StringUtils.isNotBlank(name), "name", name)
//        );
//        return new PageUtil(page);

        Page<ShippingInfoEntity> page = (Page<ShippingInfoEntity>)new PageBuilder<ShippingInfoEntity>()
                .getPage(params);
        QueryWrapper<ShippingInfoEntity> wrapper = new QueryWrapper<>();
        String name = params.get("name") == null ? null : params.get("name").toString();//发货人姓名
        wrapper.like(StringUtils.isNotBlank(name), "name", name);
        IPage<ShippingInfoEntity> iPage = baseMapper.queryPage(page, wrapper);
        return new PageUtil(iPage);
    }

    /**
     * 新增发货信息
     * @param shippingInfoEntity
     * @return
     */
    @Override
    @Transactional
    public int saveShippingInfo(ShippingInfoEntity shippingInfoEntity) {
        //是否设为默认，如果为否，无需处理，如果为是，需要把原先默认的改为非默认
        if (shippingInfoEntity.getIsDefault()) {
            //查询该商户下所有的发货地址，把原先默认的改为非默认
            Long adminUserId = shippingInfoEntity.getAdminUserId();
            ShippingInfoEntity infoEntity = this.getOne(new QueryWrapper<ShippingInfoEntity>()
                    .eq("admin_user_id", adminUserId).eq("is_default", true));
            if (infoEntity != null){
                infoEntity.setIsDefault(false);
                baseMapper.updateById(infoEntity);
            }
        }
        return baseMapper.insert(shippingInfoEntity);
    }
    /**
     * 修改发货信息
     * @param shippingInfoEntity
     * @return
     */
    @Override
    public int updateShippingInfo(ShippingInfoEntity shippingInfoEntity) {
        //是否设为默认，如果为否，无需处理，如果为是，需要把原先默认的改为非默认
        if (shippingInfoEntity.getIsDefault()) {
            //查询该商户下所有的发货地址，把原先默认的改为非默认
            Long adminUserId = shippingInfoEntity.getAdminUserId();
            ShippingInfoEntity infoEntity = this.getOne(new QueryWrapper<ShippingInfoEntity>()
                    .eq("admin_user_id", adminUserId).eq("is_default", true));
            if (infoEntity != null){
                infoEntity.setIsDefault(false);
                baseMapper.updateById(infoEntity);
            }
        }
        return baseMapper.updateById(shippingInfoEntity);
    }

    /**
     * 删除发货信息
     * @param id
     * @return
     */
    @Override
    public int deleteShippingInfo(Long id) {
        return baseMapper.deleteById(id);
    }

    @Override
    public ShippingInfoEntity getShippingInfoById(Long id) {
        return baseMapper.getShippingInfoById(id);
    }

    /**
     * 设为默认 / 取消默认
     * @param id
     * @param isDefault true:设为默认 false:取消默认
     */
    @Override
    @Transactional
    public void updateIsDefault(Long id, Boolean isDefault) {
        ShippingInfoEntity shippingInfoEntity = baseMapper.getShippingInfoById(id);
        if (!isDefault){
            shippingInfoEntity.setIsDefault(false);
            this.updateById(shippingInfoEntity);
        }else {
            List<ShippingInfoEntity> updateList = new ArrayList<>();
            shippingInfoEntity.setIsDefault(true);
            updateList.add(shippingInfoEntity);
            //查询该商户下所有的发货地址，把原先默认的改为非默认
            Long adminUserId = shippingInfoEntity.getAdminUserId();
            ShippingInfoEntity infoEntity = this.getOne(new QueryWrapper<ShippingInfoEntity>()
                    .eq("admin_user_id", adminUserId).eq("is_default", true));
            if (infoEntity != null){
                infoEntity.setIsDefault(false);
                updateList.add(infoEntity);
            }
            this.saveOrUpdateBatch(updateList);
        }

    }
}
