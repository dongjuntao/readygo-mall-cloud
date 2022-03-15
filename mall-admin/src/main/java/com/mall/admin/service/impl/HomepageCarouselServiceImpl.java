package com.mall.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.admin.entity.HomepageCarouselEntity;
import com.mall.admin.mapper.HomepageCarouselMapper;
import com.mall.admin.service.HomepageCarouselService;
import com.mall.common.util.MapUtil;
import com.mall.common.util.PageBuilder;
import com.mall.common.util.PageUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service("homepageCarouselService")
public class HomepageCarouselServiceImpl extends ServiceImpl<HomepageCarouselMapper, HomepageCarouselEntity>
        implements HomepageCarouselService {

    /**
     * 分页查询所有轮播图
     * @param params
     * @return
     */
    @Override
    public PageUtil getByPage(Map<String, Object> params) {
        //导航名称
        String name = params.get("name") == null ? null : params.get("name").toString();
        IPage<HomepageCarouselEntity> page = this.page(
                new PageBuilder<HomepageCarouselEntity>().getPage(params),
                new QueryWrapper<HomepageCarouselEntity>()
                        .like(StringUtils.isNotBlank(name), "name", name)
                        .orderByAsc("sort_num")
        );
        return new PageUtil(page);
    }

    /**
     * 根据条件查询所有轮播图
     * @param params
     * @return
     */
    @Override
    public List<HomepageCarouselEntity> getByParams(Map<String, Object> params) {
        //导航名称
        String name = params.get("name") == null ? null : params.get("name").toString();
        List<HomepageCarouselEntity> homepageCarouselEntityList = this.list(
                new QueryWrapper<HomepageCarouselEntity>()
                        .like(StringUtils.isNotBlank(name), "name", name)
                        .orderByAsc("sort_num")
        );
        return homepageCarouselEntityList;
    }

    /**
     * 保存轮播图
     * @param homepageCarouselEntity
     * @return
     */
    @Override
    public int saveHomepageCarousel(HomepageCarouselEntity homepageCarouselEntity) {
        //先判断是否有重复的
        HomepageCarouselEntity carouselEntity = this.getHomepageCarouselByParams(new MapUtil()
                .put("name",homepageCarouselEntity.getName()));
        if (carouselEntity != null) {
            return -1;
        }
        return baseMapper.insert(homepageCarouselEntity);
    }

    /**
     * 根据参数查询轮播图实体
     * @param params
     * @return
     */
    @Override
    public HomepageCarouselEntity getHomepageCarouselByParams(Map<String, Object> params) {
        //轮播图名称
        String name = params.get("name") == null ? null : params.get("name").toString();
        Long id = params.get("id") == null ? null: Long.valueOf((params.get("id").toString()));
        return baseMapper.selectOne(
                new QueryWrapper<HomepageCarouselEntity>()
                        .like(StringUtils.isNotBlank(name), "name", name)
                        .ne(id != null, "id",id));//排除id
    }

    /**
     * 修改轮播图
     * @param homepageCarouselEntity
     * @return
     */
    @Override
    public int updateHomepageCarousel(HomepageCarouselEntity homepageCarouselEntity) {
        //先判断是否有重复的
        HomepageCarouselEntity homepageCarousel = this.getHomepageCarouselByParams(new MapUtil()
                .put("name",homepageCarouselEntity.getName())
                .put("id",homepageCarouselEntity.getId()));
        if (homepageCarousel != null) {
            return -1;
        }
        return baseMapper.updateById(homepageCarouselEntity);
    }

    /**
     * 根据主键id获取轮播图实体
     * @param id
     * @return
     */
    @Override
    public HomepageCarouselEntity getHomepageCarouselById(long id) {
        return this.getById(id);
    }

    /**
     * 删除轮播图
     * @param carouselIds
     */
    @Override
    public void deleteBatch(Long[] carouselIds) {
        this.removeByIds(Arrays.asList(carouselIds));
    }

    /**
     * 启用 / 禁用
     * @param id
     * @param enable
     * @return
     */
    @Override
    public int enable(Long id, Boolean enable) {
        HomepageCarouselEntity carouselEntity = this.getById(id);
        if (carouselEntity == null) {
            return -1;
        }
        if (enable == Boolean.TRUE) {
            carouselEntity.setEnable(true);
        } else {
            carouselEntity.setEnable(false);
        }
        return this.updateById(carouselEntity) ? 1 : 0;
    }
}
