package com.mall.admin.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mall.admin.enums.RegionTypeEnum;
import lombok.Data;

/**
 * @Author DongJunTao
 * @Description 中国区域表
 * @Date 2021/8/14 22:59
 * @Version 1.0
 */
@Data
@TableName("china_region")
public class ChinaRegionEntity {

    @TableId
    private Long id;
    /**
     * 区区域名称
     */
    private String name;
    /**
     * 区域编码
     */
    private String code;
    /**
     * 区域类型
     */
    private RegionTypeEnum regionType;
    /**
     * 父区域id
     */
    private Long parentId;
}
