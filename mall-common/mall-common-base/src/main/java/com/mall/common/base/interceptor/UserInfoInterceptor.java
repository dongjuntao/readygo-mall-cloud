package com.mall.common.base.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.mall.common.base.dto.CurrentUserInfo;
import com.mall.common.base.utils.CurrentUserContextUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author DongJunTao
 * @Description 当前登录用户信息拦截
 * @Date 2022/4/26 18:19
 * @Version 1.0
 */
@Component
public class UserInfoInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String userInfo = request.getHeader("currentUserInfo");
        if (!StringUtils.isEmpty(userInfo)) {
            CurrentUserContextUtil.setCurrentUserInfo(JSONObject.parseObject(userInfo, CurrentUserInfo.class));
        }

        return true;//注意 这里必须是true否则请求将就此终止。

    }
}
