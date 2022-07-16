package com.mall.admin.controller.front;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.anji.captcha.model.common.ResponseModel;
import com.anji.captcha.model.vo.CaptchaVO;
import com.anji.captcha.service.CaptchaService;
import com.mall.admin.entity.AdminUserEntity;
import com.mall.admin.service.AdminUserService;
import com.mall.admin.service.UserRoleService;
import com.mall.auth.api.feign.FeignLoginService;
import com.mall.common.base.CommonResult;
import com.mall.common.base.constant.OAuth2Constant;
import com.mall.common.base.enums.ResultCodeEnum;
import com.mall.common.base.utils.PageUtil;
import com.mall.common.base.vo.LoginVO;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 商户信息【供门户端使用】
 * @Date 2021/4/26 14:29
 * @Version 1.0
 */
@RestController
@RequestMapping(value = "front/admin")
public class FrontAdminUserController {

    @Autowired
    private AdminUserService adminUserService;

    /**
     * 所有用户列表（不分页）
     */
    @GetMapping("/listByIds")
    public CommonResult listByIds(@RequestParam Long[] ids){
        List<AdminUserEntity> adminUserEntityList = adminUserService.getByIds(ids);
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), adminUserEntityList);
    }
}
