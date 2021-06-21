package com.mall.admin.controller;

import com.mall.admin.entity.RoleEntity;
import com.mall.admin.service.RoleMenuService;
import com.mall.admin.service.RoleService;
import com.mall.common.base.CommonResult;
import com.mall.common.base.enums.ResultCodeEnum;
import com.mall.common.redis.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2021/6/1 16:47
 * @Version 1.0
 */
@RestController
@RequestMapping("system/role")
public class RoleController {

    @Autowired
    private RoleService roleService;
    @Autowired
    private RoleMenuService roleMenuService;

    /**
     * 角色列表
     */
    @GetMapping("/list")
    public CommonResult list(@RequestParam Map<String, Object> params){

        PageUtil page = roleService.queryPage(params);
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), page);
    }

    /**
     * 角色选择
     */
    @GetMapping("/select")
    public CommonResult select(){
        Map<String, Object> map = new HashMap<>();
        List<RoleEntity> list = (List<RoleEntity>) roleService.listByMap(map);
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), list);
    }

    /**
     * 角色信息
     */
    @GetMapping("/info/{roleId}")
    public CommonResult info(@PathVariable("roleId") Long roleId) {
        RoleEntity role = roleService.getById(roleId);

        //查询角色对应的菜单
        List<Long> menuIdList = roleMenuService.queryMenuIdList(roleId);
        role.setMenuIdList(menuIdList);

        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), role);
    }

    /**
     * 保存角色
     */
    @PostMapping("/save")
    public CommonResult save(@RequestBody RoleEntity role){
        role.setCreateUserId(1L);
        roleService.saveRole(role);
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage());
    }

    /**
     * 修改角色
     */
    @PostMapping("/update")
    public CommonResult update(@RequestBody RoleEntity role){
        role.setCreateUserId(1L);
        roleService.update(role);
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage());
    }

    /**
     * 删除角色
     */
    @DeleteMapping("/delete")
    public CommonResult delete(@RequestBody Long[] roleIds){
        roleService.deleteBatch(roleIds);
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage());
    }
}
