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
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


@RestController
@RequestMapping("menu")
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
		verifyForm(menu);
		
		menuService.save(menu);
		
		return CommonResult.success("200", "成功", null);
	}
	
	/**
	 * 修改
	 */
	@PostMapping("/update")
	public CommonResult update(@RequestBody MenuEntity menu){
		//数据校验
		verifyForm(menu);
				
		menuService.updateById(menu);

		return CommonResult.success("200", "成功", null);
	}
	
	/**
	 * 删除
	 */
	@PostMapping("/delete/{menuId}")
	public CommonResult delete(@PathVariable("menuId") long menuId){
		if(menuId <= 31){
//			return R.error("系统菜单，不能删除");
		}

		//判断是否有子菜单或按钮
		List<MenuEntity> menuList = menuService.queryListParentId(menuId);
		if(menuList.size() > 0){
//			return R.error("请先删除子菜单或按钮");
		}

		menuService.delete(menuId);

		return CommonResult.success("200", "成功", null);
	}
	
	/**
	 * 验证参数是否正确
	 */
	private void verifyForm(MenuEntity menu){
		if(StringUtils.isBlank(menu.getName())){
			//throw new RRException("菜单名称不能为空");
		}
		
		if(menu.getParentId() == null){
			//throw new RRException("上级菜单不能为空");
		}
		
		//菜单
		if(menu.getType() == MenuEnum.MENU.getValue()){
			if(StringUtils.isBlank(menu.getUrl())){
				//throw new RRException("菜单URL不能为空");
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
//				throw new RRException("上级菜单只能为目录类型");
			}
			return ;
		}
		
		//按钮
		if(menu.getType() == MenuEnum.BUTTON.getValue()){
			if(parentType != MenuEnum.MENU.getValue()){
//				throw new RRException("上级菜单只能为菜单类型");
			}
			return ;
		}
	}
}
