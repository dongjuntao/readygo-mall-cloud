package com.mall.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.admin.entity.HomepageNavbarEntity;
import com.mall.admin.entity.HomepagePlateEntity;
import com.mall.admin.mapper.HomepagePlateMapper;
import com.mall.admin.service.HomepagePlateService;
import com.mall.common.util.MapUtil;
import com.mall.common.util.PageBuilder;
import com.mall.common.util.PageUtil;
import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 商城首页板块ServiceImpl
 * @Date 2022/2/10 22:55
 * @Version 1.0
 */
public class HomepagePlateServiceImpl extends ServiceImpl<HomepagePlateMapper, HomepagePlateEntity> implements HomepagePlateService {

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
                        .orderByAsc("order_num")
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
                        .orderByAsc("order_num")
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
                .put("name",homepagePlateEntity.getName()));
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
}
