package com.mall.member.controller.back;

import com.mall.common.base.CommonResult;
import com.mall.common.base.enums.ResultCodeEnum;
import com.mall.common.base.utils.CurrentUserContextUtil;
import com.mall.common.base.utils.PageUtil;
import com.mall.member.entity.MemberEntity;
import com.mall.member.service.MemberService;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 会员controller【后台管理端使用】
 * @Date 2022/4/14 20:48
 * @Version 1.0
 */
@RestController
@RequestMapping("back/member")
public class BackMemberController {

    @Autowired
    private MemberService memberService;


    /**
     * 所有用户列表
     */
    @GetMapping("/list")
    public CommonResult list(@RequestParam(value = "pageNum", required = false) Integer pageNum,
                             @RequestParam(value = "pageSize", required = false) Integer pageSize,
                             @RequestParam(value = "userName", required = false) String userName){
        PageUtil page = memberService.queryPage(pageNum, pageSize,userName);
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), page);
    }

    /**
     * 会员注册
     */
    @PostMapping("register")
    public CommonResult register(@RequestBody MemberEntity memberEntity){
        int num = memberService.register(memberEntity);
        if (num == -1){
            return CommonResult.fail(ResultCodeEnum.USER_NAME_EXIST.getCode(),ResultCodeEnum.USER_NAME_EXIST.getMessage());
        }
        return CommonResult.success();
    }

    /**
     * 根据用户名查看会员信息
     * @return
     */
    @GetMapping("getMemberByParams")
    public CommonResult getUserByParams(@RequestParam(value = "userName",required = false) String userName,
                                        @RequestParam(value = "id",required = false) Long id) {
        MemberEntity member = memberService.getMemberByParams(userName, id);
        if (member == null) {
            return null;
        }
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), member);
    }

    /**
     * 修改会员信息
     * @param memberEntity
     * @return
     */
    @PutMapping("update")
    public CommonResult update(@RequestBody MemberEntity memberEntity) {
        int num = memberService.updateMemberEntity(memberEntity);
        return num > 0 ? CommonResult.success() : CommonResult.fail();
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/delete")
    public CommonResult delete(@RequestBody Long[] memberIds){
        memberService.deleteBatch(memberIds);
        return CommonResult.success();
    }

    /**
     * 根据主键id获取用户实体
     * @param id
     * @return
     */
    @GetMapping("/getMemberById")
    public CommonResult getMemberById(@RequestParam long id) {
        MemberEntity member = memberService.getMemberById(id);
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), member);
    }
}
