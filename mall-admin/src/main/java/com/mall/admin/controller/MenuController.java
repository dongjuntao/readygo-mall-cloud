/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.mall.admin.controller;

import com.mall.admin.entity.MenuEntity;
import com.mall.admin.service.MenuService;
import com.mall.admin.service.PermissionService;
import com.mall.base.CommonResult;
import com.mall.base.enums.MenuEnum;
import com.mall.base.enums.ResultCodeEnum;
import com.mall.base.exception.CommonException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


@RestController
@RequestMapping("system/menu")
public class MenuController {

	@Autowired
	private MenuService menuService;

	@Autowired
	private PermissionService permissionService;

	/**
	 * 导航菜单
	 */
	@GetMapping("/navbar")
	public CommonResult nav(){
		List<MenuEntity> menuList = menuService.getUserMenuList(1L);
		Set<String> permissions = permissionService.getUserPermissions(1L);
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("menuList", menuList);
		resultMap.put("permissions", permissions);
		return CommonResult.success(resultMap);
	}
	
	/**
	 * 所有菜单列表
	 */
	@GetMapping("/list")
	public List<MenuEntity> list(){
		List<MenuEntity> menuList = menuService.list();
		for(MenuEntity menu : menuList){
			MenuEntity parentMenuEntity = menuService.getById(menu.getParentId());
			if(parentMenuEntity != null){
				menu.setParentName(parentMenuEntity.getName());
			}
		}
		return menuList;
	}
	
	/**
	 * 选择菜单(添加、修改菜单)
	 */
	@GetMapping("/select")
	public CommonResult select(){
		//查询列表数据
		List<MenuEntity> menuList = menuService.queryNotButtonList();
		//添加顶级菜单
		MenuEntity root = new MenuEntity();
		root.setId(0L);
		root.setName("一级菜单");
		root.setParentId(-1L);
		root.setOpen(true);
		menuList.add(root);
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("menuList", menuList);

		return CommonResult.success(resultMap);
	}
	
	/**
	 * 菜单信息
	 */
	@GetMapping("/info/{menuId}")
	public CommonResult info(@PathVariable("menuId") Long menuId){
		MenuEntity menu = menuService.getById(menuId);
		Map<String, Object> map = new HashMap<>();
		map.put("menu", menu);
		return CommonResult.success(map);
	}
	
	/**
	 * 保存
	 */
	@PostMapping("/save")
	public CommonResult save(@RequestBody MenuEntity menu){
		//数据校验
		CommonResult result = verifyForm(menu);
		if (result != null) return result;
		menuService.save(menu);
		return CommonResult.success();
	}
	
	/**
	 * 修改
	 */
	@PutMapping("/update")
	public CommonResult update(@RequestBody MenuEntity menu){
		//数据校验
		CommonResult result = verifyForm(menu);
		if (result != null) return result;
		menuService.updateById(menu);
		return CommonResult.success();
	}
	
	/**
	 * 删除
	 */
	@DeleteMapping("/delete/{menuId}")
	public CommonResult delete(@PathVariable("menuId") long menuId){
		//判断是否有子菜单或按钮
		List<MenuEntity> menuList = menuService.queryListParentId(menuId);
		if(menuList.size() > 0){
			return CommonResult.fail(ResultCodeEnum.PLEASE_DELETE_CHILD_MENU_BUTTON.getCode(),
					ResultCodeEnum.PLEASE_DELETE_CHILD_MENU_BUTTON.getMessage());
		}
		menuService.delete(menuId);
		return CommonResult.success();
	}
	
	/**
	 * 验证参数是否正确
	 */
	private CommonResult verifyForm(MenuEntity menu) {
		if(StringUtils.isBlank(menu.getName())){
			return CommonResult.fail(ResultCodeEnum.MENU_NAME_NOT_BE_EMPTY.getCode(),
					ResultCodeEnum.MENU_NAME_NOT_BE_EMPTY.getMessage());
		}
		if(menu.getParentId() == null){
			return CommonResult.fail(ResultCodeEnum.PARENT_MENU_NOT_BE_EMPTY.getCode(),
					ResultCodeEnum.PARENT_MENU_NOT_BE_EMPTY.getMessage());
		}
		//菜单
		if(menu.getType() == MenuEnum.MENU.getValue()){
			if(StringUtils.isBlank(menu.getUrl())){
				return CommonResult.fail(ResultCodeEnum.MENU_URL_NOT_BE_EMPTY.getCode(),
						ResultCodeEnum.MENU_URL_NOT_BE_EMPTY.getMessage());
			}
		}
		
		//上级菜单类型
		int parentType = MenuEnum.CATALOG.getValue();
		if(menu.getParentId() != 0){
			MenuEntity parentMenu = menuService.getById(menu.getParentId());
			parentType = parentMenu.getType();
		}
		//目录、菜单
		if(menu.getType() == MenuEnum.CATALOG.getValue() ||
				menu.getType() == MenuEnum.MENU.getValue()){
			if(parentType != MenuEnum.CATALOG.getValue()){
				return CommonResult.fail(ResultCodeEnum.PARENT_MENU_IS_ONLY_CATALOG.getCode(),
						ResultCodeEnum.PARENT_MENU_IS_ONLY_CATALOG.getMessage());
			}
		}
		//按钮
		if(menu.getType() == MenuEnum.BUTTON.getValue()){
			if(parentType != MenuEnum.MENU.getValue()){
				return CommonResult.fail(ResultCodeEnum.PARENT_MENU_IS_ONLY_MENU.getCode(),
						ResultCodeEnum.PARENT_MENU_IS_ONLY_MENU.getMessage());
			}
		}
		return null;
	}
}
