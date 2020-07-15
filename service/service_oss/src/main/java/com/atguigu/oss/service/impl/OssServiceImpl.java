package com.atguigu.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectResult;
import com.atguigu.oss.service.OssService;
import com.atguigu.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sun.reflect.misc.ConstructorUtil;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {
    @Override
    public String uploadFileAvatar(MultipartFile file) {
        String endpoint = ConstantPropertiesUtils.END_POINT;
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId = ConstantPropertiesUtils.KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;


        try {
            // 创建OSSClient实例
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            // 上传文件流
            InputStream inputStream = file.getInputStream();

            // 获取文件名称
            String filename = file.getOriginalFilename();
            
            // 随机唯一UUID
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            
            filename = uuid+filename;

            String datePath = new DateTime().toString("yyyy/MM/dd");
            
            filename = datePath+"/"+filename;

            PutObjectResult putObjectResult = ossClient.putObject(bucketName, filename, inputStream);
            
            // 关闭OSSClient
            ossClient.shutdown();

            // 手动拼接路径
            // https://edu-al.oss-cn-beijing.aliyuncs.com/1594305156343.jpeg
            // oss-cn-beijing.aliyuncs.com
            String url = "https://"+bucketName+"."+endpoint+"/"+filename;
            return url;
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }

    }
}
