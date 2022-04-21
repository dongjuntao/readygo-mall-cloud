package com.mall.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.admin.entity.HomepagePlateEntity;
import com.mall.admin.entity.HomepagePlateGoodsRelatedEntity;
import com.mall.admin.mapper.HomepagePlateMapper;
import com.mall.admin.service.HomepagePlateGoodsRelatedService;
import com.mall.admin.service.HomepagePlateService;
import com.mall.common.base.utils.MapUtil;
import com.mall.common.base.utils.PageBuilder;
import com.mall.common.base.utils.PageUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 商城首页板块ServiceImpl
 * @Date 2022/2/10 22:55
 * @Version 1.0
 */
@Service("homepagePlateService")
public class HomepagePlateServiceImpl extends ServiceImpl<HomepagePlateMapper, HomepagePlateEntity> implements HomepagePlateService {

    @Autowired
    private HomepagePlateGoodsRelatedService homepagePlateGoodsRelatedService;

    /**
     * 分页查询所有板块
     * @param params
     * @return
     */
    @Override
    public PageUtil getByPage(Map<String, Object> params) {
        //导航名称
        String name = params.get("name") == null ? null : params.get("name").toString();
        IPage<HomepagePlateEntity> page = this.page(
                new PageBuilder<HomepagePlateEntity>().getPage(params),
                new QueryWrapper<HomepagePlateEntity>()
                        .like(StringUtils.isNotBlank(name), "name", name)
        );
        return new PageUtil(page);
    }

    /**
     * 根据条件查询所有板块
     * @param params
     * @return
     */
    @Override
    public List<HomepagePlateEntity> getByParams(Map<String, Object> params) {
        //导航名称
        String name = params.get("name") == null ? null : params.get("name").toString();
        List<HomepagePlateEntity> homepagePlateEntityList = this.list(
                new QueryWrapper<HomepagePlateEntity>()
                        .like(StringUtils.isNotBlank(name), "name", name)
        );
        return homepagePlateEntityList;
    }

    /**
     * 保存板块
     * @param homepagePlateEntity
     * @return
     */
    @Override
    public int saveHomepagePlate(HomepagePlateEntity homepagePlateEntity) {
        //先判断是否有重复的
        HomepagePlateEntity homepagePlate = this.getHomepagePlateByParams(new MapUtil()
                .put("name",homepagePlateEntity.getName()));
        if (homepagePlate != null) {
            return -1;
        }
        return baseMapper.insert(homepagePlateEntity);
    }

    /**
     * 根据参数查询板块实体
     * @param params
     * @return
     */
    @Override
    public HomepagePlateEntity getHomepagePlateByParams(Map<String, Object> params) {
        //导航名称
        String name = params.get("name") == null ? null : params.get("name").toString();
        Long id = params.get("id") == null ? null: Long.valueOf((params.get("id").toString()));
        return baseMapper.selectOne(
                new QueryWrapper<HomepagePlateEntity>()
                        .like(StringUtils.isNotBlank(name), "name", name)
                        .ne(id != null, "id",id));//排除id
    }

    /**
     * 修改板块
     * @param homepagePlateEntity
     * @return
     */
    @Override
    public int updateHomepagePlate(HomepagePlateEntity homepagePlateEntity) {
        //先判断是否有重复的
        HomepagePlateEntity homepagePlate = this.getHomepagePlateByParams(new MapUtil()
                .put("name",homepagePlateEntity.getName())
                .put("id",homepagePlateEntity.getId()));
        if (homepagePlate != null) {
            return -1;
        }
        return baseMapper.updateById(homepagePlateEntity);
    }

    /**
     * 根据主键id获取板块实体
     * @param id
     * @return
     */
    @Override
    public HomepagePlateEntity getHomepagePlateById(long id) {
        return this.getById(id);
    }

    /**
     * 删除板块
     * @param plateIds
     */
    @Override
    public void deleteBatch(Long[] plateIds) {
        this.removeByIds(Arrays.asList(plateIds));
    }

    /**
     * 启用 / 禁用
     * @param id
     * @param enable
     * @return
     */
    @Override
    public int enable(Long id, Boolean enable) {
        HomepagePlateEntity homepagePlateEntity = this.getById(id);
        if (homepagePlateEntity == null) {
            return -1;
        }
        if (enable == Boolean.TRUE) {
            homepagePlateEntity.setEnable(true);
        } else {
            homepagePlateEntity.setEnable(false);
        }
        return this.updateById(homepagePlateEntity) ? 1 : 0;
    }

    /**
     * 关联商品
     * @param id
     * @param goodsIds
     */
    @Override
    @Transactional
    public void relateGoods(Long id, Long[] goodsIds) {
        //先查询该板块是否有关联的商品，如果有，先全部删除
       List<HomepagePlateGoodsRelatedEntity> plateGoodsRelatedEntityList =
               homepagePlateGoodsRelatedService.getHomepagePlateGoodsRelatedList(id);
       if (!CollectionUtils.isEmpty(plateGoodsRelatedEntityList)) {
           homepagePlateGoodsRelatedService.deleteBatch(id);
       }
        //批量新增商品关联信息
        homepagePlateGoodsRelatedService.save(id, goodsIds);
    }

    /**
     * 根据板块id获取关联商品
     * @param plateId
     * @return
     */
    @Override
    public List<HomepagePlateGoodsRelatedEntity> getRelatedGoods(Long plateId) {
        return homepagePlateGoodsRelatedService.getHomepagePlateGoodsRelatedList(plateId);
    }
}
