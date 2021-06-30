package com.mall.common.base.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2021/6/3 10:59
 * @Version 1.0
 */
@Data
public class AdminUserDTO {

    /**
     * 用户主键id
     */
    private Long id;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 姓名
     */
    private String name;
    /**
     * 密码
     */
    private String password;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 状态  0：禁用   1：正常
     */
    private Integer status;
    /**
     * 创建者ID
     */
    private Long createUserId;
    /**
     * 角色ID列表
     */
    private List<Long> roleIdList = new ArrayList<>();
    /**
     * 角色名称列表
     */
    private List<String> roleNameList = new ArrayList<>();
}
