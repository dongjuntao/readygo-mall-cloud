package com.mall.member.vo;

import lombok.Data;

/**
 * @Author DongJunTao
 * @Description 店铺收藏信息
 * @Date 2022/5/2 16:28
 * @Version 1.0
 */
@Data
public class CollectShopVO {
    /**
     * 店铺id
     */
    private Long id;
    /**
     * 店铺名称
     */
    private String name;
    /**
     * 店铺logo
     */
    private String avatar;
}
