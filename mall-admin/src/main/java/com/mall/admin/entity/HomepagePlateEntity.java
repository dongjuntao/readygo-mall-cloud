package com.mall.admin.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author DongJunTao
 * @Description 商城首页板块
 * @Date 2022/2/10 21:55
 * @Version 1.0
 */
@Data
@TableName("homepage_plate")
public class HomepagePlateEntity {

    @TableId
    private Long id;

    /**
     * 板块名称
     */
    private String name;

    /**
     * 板块二级名称
     */
    private String secondName;

    /**
     * 板块内最大容纳商品数量
     */
    private Integer maxLimit;

    /**
     * 是否启用
     */
    private Boolean enable;
}
