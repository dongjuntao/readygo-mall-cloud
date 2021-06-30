package com.mall.brand.controller;

import com.mall.brand.entity.BrandEntity;
import com.mall.brand.service.BrandService;
import com.mall.common.base.CommonResult;
import com.mall.common.file.config.QCloudCosUtilsConfig;
import com.mall.common.file.util.QCloudCosUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 品牌logo文件上传下载
 * @Date 2021/6/16 16:42
 * @Version 1.0
 */
@RestController
@RequestMapping("brand")
public class FileController {

    @Autowired
    private QCloudCosUtils qCloudCosUtils;

    @Autowired
    private BrandService brandService;

    /**
     * 品牌logo上传
     * @param files 文件数组，支持单个和多个文件上传
     * @param folderName 上传的文件路径名称
     * @return
     */
    @PostMapping("logo/upload")
    public CommonResult upload(@RequestParam("files") MultipartFile[] files,
                               @RequestParam("folderName") String folderName) {
       return CommonResult.success(qCloudCosUtils.upload(files, folderName));
    }

    @DeleteMapping("logo/delete")
    public CommonResult delete(@RequestParam("filePath") String filePath,
                               @RequestParam("folderName") String folderName) {
        qCloudCosUtils.delete(filePath, folderName);
        //删除后清空品牌表中logo字段
        Map<String, Object> map = new HashMap<>();
        map.put("logo", filePath);
        BrandEntity brandEntity = brandService.queryByParams(map);
        brandEntity.setLogo("");
        brandService.saveOrUpdate(brandEntity);
        return CommonResult.success();

    }
}
