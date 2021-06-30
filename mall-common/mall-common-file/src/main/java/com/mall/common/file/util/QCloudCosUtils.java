package com.mall.common.file.util;

import com.mall.common.file.property.QCloudCosProperty;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.region.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @Author DongJunTao
 * @Description 腾讯云对象存储cos工具类
 * @Date 2021/6/16 9:18
 * @Version 1.0
 */
public class QCloudCosUtils {

    @Autowired
    private QCloudCosProperty property;

    /**
     * upload()重载方法
     * @param multipartFiles 文件数组
     * @param folderName 上传的文件夹名称
     * @return 上传文件在存储桶的链接
     */
    public String upload(MultipartFile[] multipartFiles, String folderName) {
        for (MultipartFile multipartFile : multipartFiles)  {
            //生成唯一文件名
            String newFileName = generateUniqueName(multipartFile.getOriginalFilename());
            //文件在存储桶中的key
            String key = folderName + newFileName;
            //声明客户端
            COSClient cosClient = null;
            //准备将MultipartFile类型转为File类型
            File file = null;
            try {
                //生成临时文件
                file = File.createTempFile("temp",null);
                //将MultipartFile类型转为File类型
                multipartFile.transferTo(file);
                //初始化用户身份信息(secretId,secretKey)
                COSCredentials cosCredentials = new BasicCOSCredentials(property.getSecretId(), property.getSecretKey());
                //设置bucket的区域
                ClientConfig clientConfig = new ClientConfig(new Region(property.getRegion()));
                //生成cos客户端
                cosClient = new COSClient(cosCredentials, clientConfig);
                //创建存储对象的请求
                PutObjectRequest putObjectRequest = new PutObjectRequest(property.getBucketName(), key, file);
                //执行上传并返回结果信息
                cosClient.putObject(putObjectRequest);
                return property.getUrl()+key;
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                cosClient.shutdown();
            }
        }
        return null;
    }

    /**
     * 删除文件
     * @param filePath
     * @param folderName
     */
    public void delete(String filePath, String folderName) {
        //文件在存储桶中的key
        String key = filePath.substring(filePath.indexOf(folderName));
        //声明客户端
        COSClient cosClient=null;
        try {
            COSCredentials cosCredentials = new BasicCOSCredentials(property.getSecretId(), property.getSecretKey());
            //设置bucket的区域
            ClientConfig clientConfig = new ClientConfig(new Region(property.getRegion()));
            //生成cos客户端
            cosClient = new COSClient(cosCredentials, clientConfig);
            cosClient.deleteObject(property.getBucketName(), key);
        }finally {
            cosClient.shutdown();
        }
    }

    /**
     * 生成唯一文件名（当前时间毫秒数+6位随机数）
     * @param originalName
     * @return
     */
    public String generateUniqueName(String originalName) {
        return System.currentTimeMillis() + "" + (int)((Math.random()*9+1)*100000) + originalName;
    }

}
