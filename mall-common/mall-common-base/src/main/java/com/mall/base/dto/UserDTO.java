package com.mall.base.dto;

import lombok.Data;

/**
 * @Author DongJunTao
 * @Description 用户登录信息
 * @Date 2021/4/27 17:28
 * @Version 1.0
 */
@Data
public class UserDTO {

    private long id;

    private String userName;

    private String password;

    private String email;

    private String mobile;

    private int age;
}
