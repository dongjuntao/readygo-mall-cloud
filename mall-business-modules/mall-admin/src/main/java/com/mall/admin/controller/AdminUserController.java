package com.mall.admin.controller;

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
 * @Description 管理员用户后端控制器
 * @Date 2021/4/26 14:29
 * @Version 1.0
 */
@RestController
@RequestMapping(value = "system/admin")
public class AdminUserController {

    @Autowired
    private CaptchaService captchaService;
    @Autowired
    private AdminUserService adminUserService;
    @Autowired
    private FeignLoginService feignLoginService;
    @Autowired
    private UserRoleService userRoleService;

    /**
     * 登录后从认证中心获取token
     * @param loginVO
     * @param captchaVerification
     * @return
     */
    @PostMapping("/login")
    public CommonResult postAccessToken(@RequestBody LoginVO loginVO,
                             @RequestParam("captchaVerification") String captchaVerification) {
        CaptchaVO captchaVO = new CaptchaVO();
        captchaVO.setCaptchaVerification(captchaVerification);
        //登录时滑动验证码二次验证
        ResponseModel response = captchaService.verification(captchaVO);
        if(!response.isSuccess()){
            //校验失败，返回到前端
            return CommonResult.fail(response.getRepCode(), response.getRepMsg());
        }
        //认证，获取token
        long start = System.currentTimeMillis();
        String resultMap = feignLoginService.login(loginVO.getUserName(),loginVO.getPassword(),
                loginVO.getUserType(), OAuth2Constant.ADMIN_CLIENT_ID);
        CommonResult result = JSON.parseObject(resultMap, CommonResult.class);
        if (StringUtils.isEmpty(resultMap) || result == null) {
            return CommonResult.fail();
        }
        return result;
    }

    @DeleteMapping("/logout")
    public CommonResult logout(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (StringUtils.isEmpty(token)) {
            return CommonResult.fail();
        }
        String result = feignLoginService.loginOut(token.replace("Bearer ", ""));
        JSONObject jsonResult = JSON.parseObject(result);
        if (!"200".equals(jsonResult.get("code"))) {
            //校验失败，返回到前端
            return CommonResult.fail(String.valueOf(jsonResult.get("code")), String.valueOf(jsonResult.get("message")));
        }
        return CommonResult.success();
    }

    /**
     * 保存用户
     */
    @PostMapping("/save")
    public CommonResult save(@RequestBody AdminUserEntity adminUserEntity){
        int num = adminUserService.saveAdmin(adminUserEntity);
        if (num == -1){
            return CommonResult.fail(ResultCodeEnum.USER_NAME_EXIST.getCode(),ResultCodeEnum.USER_NAME_EXIST.getMessage());
        }
        return CommonResult.success();
    }

    /**
     * 修改用户
     */
    @PutMapping("/update")
    public CommonResult update(@RequestBody AdminUserEntity adminUserEntity){
        adminUserEntity.setUpdateTime(new Date());
        int num = adminUserService.update(adminUserEntity);
        if (num == -1){
            return CommonResult.fail(ResultCodeEnum.USER_NAME_EXIST.getCode(),ResultCodeEnum.USER_NAME_EXIST.getMessage());
        }
        return CommonResult.success();
    }

    /**
     * 分页查询所有用户
     * @param userName 用户名
     * @param userType 用户类型  0:【平台管理员】（该类管理员属于平台所有，管理平台相关事宜） 1:【店铺管理员】包括【入驻商户】【自营商户】
     * @param merchantType 商户（店铺）类型 0:入驻商户 1:自营商户
     * @param authStatus 审核状态
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @return
     */
    @GetMapping("/list")
    public CommonResult list(@RequestParam(value = "userName", required = false) String userName,
                             @RequestParam(value = "userType", required = false) Integer userType,
                             @RequestParam(value = "merchantType", required = false) Integer merchantType,
                             @RequestParam(value = "authStatus", required = false) Integer authStatus,
                             @RequestParam(value = "pageNum", required = false) Integer pageNum,
                             @RequestParam(value = "pageSize", required = false) Integer pageSize){
        PageUtil page = adminUserService.queryPage(userName,userType,merchantType,authStatus,pageNum,pageSize);
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), page);
    }

    /**
     * 所有用户列表（不分页）
     */
    @GetMapping("/listAll")
    public CommonResult listAll(@RequestParam(value = "userType", required = false) Integer userType,
                                @RequestParam(value = "authStatus", required = false) Integer authStatus,
                                @RequestParam(value = "merchantType", required = false) Integer merchantType){
        List<AdminUserEntity> adminUserEntityList = adminUserService.queryByParams(userType, authStatus, merchantType);
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), adminUserEntityList);
    }

    /**
     * 根据主键id获取用户实体
     * @param id
     * @return
     */
    @GetMapping("/getUserById")
    public CommonResult getAdminUserById(@RequestParam long id) {
        AdminUserEntity adminUser = adminUserService.getAdminUserById(id);
        //获取用户所属的角色列表
        List<Long> roleIdList = userRoleService.queryRoleIdList(id);
        adminUser.setRoleIdList(roleIdList);
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), adminUser);
    }

    /**
     * 根据用户名和用户类型查看用户
     * @param userName 用户名
     * @param userType 用户类型  0:【平台管理员】（该类管理员属于平台所有，管理平台相关事宜） 1:【店铺管理员】包括【入驻商户】【自营商户】
     * @param id 用户id
     * @return
     */
    @GetMapping("getUserByParams")
    public CommonResult getUserByParams(@RequestParam(value = "userName", required = false) String userName,
                                        @RequestParam(value = "userType", required = false) Integer userType,
                                        @RequestParam(value = "id", required = false) Long id) {
        AdminUserEntity adminUser = adminUserService.getUserByParams(userName, userType, id);
        if (adminUser == null) {
            return null;
        }
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), adminUser);
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/delete")
    public CommonResult delete(@RequestBody Long[] userIds){
        //管理员账号禁止删除
        if(ArrayUtils.contains(userIds, 1)){
            return CommonResult.fail(ResultCodeEnum.ADMIN_COUNT_NOT_DELETED.getCode(),ResultCodeEnum.ADMIN_COUNT_NOT_DELETED.getMessage());
        }
        adminUserService.deleteBatch(userIds);
        return CommonResult.success();
    }

    /**
     * 根据ids集合所有用户列表（不分页）
     */
    @GetMapping("/listByIds")
    public CommonResult listByIds(@RequestParam Long[] ids){
        List<AdminUserEntity> adminUserEntityList = adminUserService.getByIds(ids);
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), adminUserEntityList);
    }

    /**
     * 商家审核
     */
    @PutMapping("/audit/{id}")
    public CommonResult audit(@PathVariable("id") Long id,
                              @RequestBody AdminUserEntity entity) {
        int num = adminUserService.audit(id, entity);
        if (num == -1){
            return CommonResult.fail(ResultCodeEnum.USER_ACCOUNT_NOT_EXIST.getCode(),ResultCodeEnum.USER_ACCOUNT_NOT_EXIST.getMessage());
        }
        return CommonResult.success();
    }
}
