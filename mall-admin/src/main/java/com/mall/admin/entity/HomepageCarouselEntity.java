package com.mall.admin.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author DongJunTao
 * @Description 首页轮播图
 * @Date 2022/3/15 20:05
 * @Version 1.0
 */
@Data
@TableName("homepage_carousel")
public class HomepageCarouselEntity {

    @TableId
    private Long id;

    /**
     * 轮播图名称
     */
    private String name;

    /**
     * 管理的商品id
     */
    private Long goodsId;

    /**
     * 跳转地址
     */
    private String url;

    /**
     * 排序
     */
    private Integer sortNum;

    /**
     * 是否启用
     */
    private Boolean enable;
}
