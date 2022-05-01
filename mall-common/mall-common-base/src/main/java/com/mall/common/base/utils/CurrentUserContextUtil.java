package com.mall.common.base.utils;

import com.mall.common.base.dto.CurrentUserInfo;

/**
 * @Author DongJunTao
 * @Description 登录用户信息工具类
 * @Date 2022/4/28 19:54
 * @Version 1.0
 */
public class CurrentUserContextUtil {

    public static ThreadLocal<CurrentUserInfo> currentUserInfo = new ThreadLocal<>();

    /**
     * threadLocal中放置当前登录的用户信息
     * @param userInfo
     */
    public static void setCurrentUserInfo (CurrentUserInfo userInfo) {
        currentUserInfo.set(userInfo);
    }

    /**
     * threadLocal中获取当前登录的用户信息
     * @return
     */
    public static CurrentUserInfo getCurrentUserInfo() {
       return currentUserInfo.get();
    }
}
