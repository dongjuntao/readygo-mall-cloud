package com.mall.file.controller;

import com.mall.common.base.CommonResult;
import com.mall.file.util.QCloudCosUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author DongJunTao
 * @Description 文件服务controller
 * @Date 2021/7/11 10:53
 * @Version 1.0
 */
@RestController
@RequestMapping("file")
public class FileController {

    @Autowired
    private QCloudCosUtils qCloudCosUtils;

    /**
     * @param files 文件数组，支持单个和多个文件上传
     * @param folderName 上传的文件路径名称
     * @return
     */
    @PostMapping("upload")
    public CommonResult upload(@RequestParam("files") MultipartFile[] files,
                               @RequestParam("folderName") String folderName) {
        return CommonResult.success(qCloudCosUtils.upload(files, folderName));
    }

    /**
     * @param filePath
     * @param folderName
     * @return
     */
    @DeleteMapping("delete")
    public CommonResult delete(@RequestParam("filePath") String filePath,
                               @RequestParam("folderName") String folderName) {
        qCloudCosUtils.delete(filePath, folderName);
        return CommonResult.success();

    }
}
