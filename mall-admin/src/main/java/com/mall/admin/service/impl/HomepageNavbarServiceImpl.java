package com.mall.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.admin.entity.HomepageNavbarEntity;
import com.mall.admin.mapper.HomepageNavbarMapper;
import com.mall.admin.service.HomepageNavbarService;
import com.mall.common.util.MapUtil;
import com.mall.common.util.PageBuilder;
import com.mall.common.util.PageUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 首页导航栏ServiceImpl
 * @Date 2022/2/10 22:56
 * @Version 1.0
 */
@Service
public class HomepageNavbarServiceImpl extends ServiceImpl<HomepageNavbarMapper, HomepageNavbarEntity> implements HomepageNavbarService {

    /**
     * 分页查询所有导航
     * @param params
     * @return
     */
    @Override
    public PageUtil getByPage(Map<String, Object> params) {
        //导航名称
        String name = params.get("name") == null ? null : params.get("name").toString();
        IPage<HomepageNavbarEntity> page = this.page(
                new PageBuilder<HomepageNavbarEntity>().getPage(params),
                new QueryWrapper<HomepageNavbarEntity>()
                        .like(StringUtils.isNotBlank(name), "name", name)
                        .orderByAsc("order_num")
        );
        return new PageUtil(page);
    }

    /**
     * 根据条件查询所有导航
     * @param params
     * @return
     */
    @Override
    public List<HomepageNavbarEntity> getByParams(Map<String, Object> params) {
        //导航名称
        String name = params.get("name") == null ? null : params.get("name").toString();
        List<HomepageNavbarEntity> homepageNavbarEntityList = this.list(
                new QueryWrapper<HomepageNavbarEntity>()
                        .like(StringUtils.isNotBlank(name), "name", name)
                        .orderByAsc("order_num")
        );
        return homepageNavbarEntityList;
    }

    /**
     * 保存导航
     * @param homepageNavbarEntity
     * @return
     */
    @Override
    public int saveHomepageNavbar(HomepageNavbarEntity homepageNavbarEntity) {
        //先判断是否有重复的
        HomepageNavbarEntity homepageNavbar = this.getHomepageNavbarByParams(new MapUtil()
                .put("name",homepageNavbarEntity.getName()));
        if (homepageNavbar != null) {
            return -1;
        }
        return baseMapper.insert(homepageNavbarEntity);
    }

    /**
     * 根据参数查询导航实体
     * @param params
     * @return
     */
    @Override
    public HomepageNavbarEntity getHomepageNavbarByParams(Map<String, Object> params) {
        //导航名称
        String name = params.get("name") == null ? null : params.get("name").toString();
        Long id = params.get("id") == null ? null: Long.valueOf((params.get("id").toString()));
        return baseMapper.selectOne(
                new QueryWrapper<HomepageNavbarEntity>()
                        .like(StringUtils.isNotBlank(name), "name", name)
                        .ne(id != null, "id",id));//排除id
    }

    /**
     * 修改导航
     * @param homepageNavbarEntity
     * @return
     */
    @Override
    public int updateHomepageNavbar(HomepageNavbarEntity homepageNavbarEntity) {
        //先判断是否有重复的
        HomepageNavbarEntity homepageNavbar = this.getHomepageNavbarByParams(new MapUtil()
                .put("name",homepageNavbarEntity.getName()));
        if (homepageNavbar != null) {
            return -1;
        }
        return baseMapper.updateById(homepageNavbarEntity);
    }

    /**
     * 根据主键id获取导航实体
     * @param id
     * @return
     */
    @Override
    public HomepageNavbarEntity getHomepageNavbarById(long id) {
        return this.getById(id);
    }

    /**
     * 删除导航
     * @param navbarIds
     */
    @Override
    public void deleteBatch(Long[] navbarIds) {
        this.removeByIds(Arrays.asList(navbarIds));
    }
}
