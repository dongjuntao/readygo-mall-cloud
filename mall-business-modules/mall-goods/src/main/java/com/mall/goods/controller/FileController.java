package com.mall.goods.controller;

import com.mall.common.base.CommonResult;
import com.mall.common.file.util.QCloudCosUtils;
import com.mall.goods.entity.GoodsSpecificationsEntity;
import com.mall.goods.service.GoodsSpecificationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 商品规格绑定图片上传下载
 * @Date 2021/6/29 16:42
 * @Version 1.0
 */
@RestController
@RequestMapping("goods")
public class FileController {

    @Autowired
    private QCloudCosUtils qCloudCosUtils;

    /**
     * 商品规格绑定图片上传
     * @param files 文件数组，支持单个和多个文件上传
     * @param folderName 上传的文件路径名称
     * @return
     */
    @PostMapping("specificationsImage/upload")
    public CommonResult upload(@RequestParam("files") MultipartFile[] files,
                               @RequestParam("folderName") String folderName) {
       return CommonResult.success(qCloudCosUtils.upload(files, folderName));
    }

    /**
     * 删除商品规格绑定图片
     * @param filePath
     * @param folderName
     * @return
     */
    @DeleteMapping("specificationsImage/delete")
    public CommonResult delete(@RequestParam("filePath") String filePath,
                               @RequestParam("folderName") String folderName) {
        qCloudCosUtils.delete(filePath, folderName);
        return CommonResult.success();

    }
}
