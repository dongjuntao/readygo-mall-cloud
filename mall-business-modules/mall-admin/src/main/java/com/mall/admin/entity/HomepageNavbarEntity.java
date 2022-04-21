package com.mall.admin.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author DongJunTao
 * @Description 首页导航栏
 * @Date 2022/2/10 21:49
 * @Version 1.0
 */
@Data
@TableName("homepage_navbar")
public class HomepageNavbarEntity {

    @TableId
    private Long id;

    /**
     * 导航名称
     */
    private String name;

    /**
     * 导航链接
     */
    private String linkUrl;

    /**
     * 是否启用
     */
    private Boolean enable;

    /**
     * 排序号
     */
    private Integer sortNum;
}
