package com.mall.common.base.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author DongJunTao
 * @Description 用户登录参数
 * @Date 2021/4/27 17:28
 * @Version 1.0
 */
@Data
@NoArgsConstructor
public class LoginVO {

    private String userName;

    private String password;

}
