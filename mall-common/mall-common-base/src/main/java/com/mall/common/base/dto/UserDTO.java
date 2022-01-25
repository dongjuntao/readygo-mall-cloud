package com.mall.common.base.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2021/6/3 22:59
 * @Version 1.0
 */
@Data
public class UserDTO {
    /**
     * 用户主键id
     */
    private Long id;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String password;
    /**
     * 用户类型（0：系统管理员，1：商户管理员）
     */
    private Integer userType;
    /**
     * 审核状态（0：待审核  1：通过审核  2：未通过审核）
     */
    private Integer authStatus;
    /**
     * 头像信息
     */
    private String avatar;
}
