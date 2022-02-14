package com.mall.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.admin.entity.HomepagePlateEntity;
import com.mall.common.util.PageUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 商城首页板块Service
 * @Date 2022/2/10 22:55
 * @Version 1.0
 */
@Service
public interface HomepagePlateService extends IService<HomepagePlateEntity> {

    /**
     * 分页查询板块列表
     * @param params
     * @return
     */
    PageUtil getByPage(Map<String, Object> params);

    /**
     * 根据条件查询所有板块
     * @param params
     * @return
     */
    List<HomepagePlateEntity> getByParams(Map<String, Object> params);

    /**
     * 保存板块
     * @param homepagePlateEntity
     * @return
     */
    int saveHomepagePlate(HomepagePlateEntity homepagePlateEntity);

    /**
     * 根据参数查询板块实体
     * @param params
     * @return
     */
    HomepagePlateEntity getHomepagePlateByParams(Map<String, Object> params);

    /**
     * 修改板块
     * @param homepagePlateEntity
     * @return
     */
    int updateHomepagePlate(HomepagePlateEntity homepagePlateEntity);

    /**
     * 根据主键id获取板块实体
     * @param id
     * @return
     */
    HomepagePlateEntity getHomepagePlateById(long id);

    /**
     * 删除板块（支持批量）
     * @param plateIds
     */
    void deleteBatch(Long[] plateIds);

    /**
     * 启用 / 禁用
     */
    int enable(Long id, Boolean enable);
}
