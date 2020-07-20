package com.atguigu.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.atguigu.commonutils.R;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.atguigu.vod.service.VodService;
import com.atguigu.vod.utils.ConstantVodUtils;
import com.atguigu.vod.utils.InitVodClient;
import com.sun.media.sound.SoftTuning;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class VodServiceImpl implements VodService {

    // 上传视频
    @Override
    public String uploadVideoAly(MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();

            // 文件原始名称
            String fileName = file.getOriginalFilename();

            // vod中的文件显示名称
            String title = fileName.substring(0, fileName.lastIndexOf("."));


            UploadStreamRequest request = new UploadStreamRequest(ConstantVodUtils.ACCESS_KEY_ID,
                    ConstantVodUtils.ACCESS_KEY_SECRET,
                    title,
                    fileName,
                    inputStream);


            System.out.println(ConstantVodUtils.ACCESS_KEY_ID);
            System.out.println(ConstantVodUtils.ACCESS_KEY_SECRET);

            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);

            String videoId = null;

            // System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
            if (response.isSuccess()) {
                // System.out.print("VideoId=" + response.getVideoId() + "\n");
                videoId = response.getVideoId();

            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因

                videoId = response.getVideoId();
                // System.out.print("VideoId=" + response.getVideoId() + "\n");
                // System.out.print("ErrorCode=" + response.getCode() + "\n");
                // System.out.print("ErrorMessage=" + response.getMessage() + "\n");
            }

            return videoId;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 删除多个阿里视频
    @Override
    public void removeMoreAlyVideo(List videoIdList) {
        try {
            DefaultAcsClient client = InitVodClient.initVodClient();
            // 删除视频request
            DeleteVideoRequest request = new DeleteVideoRequest();
            // 设置视频id
            // 转换List为 1,2,3 这种格式
            String videoIds = StringUtils.join(videoIdList.toArray(), ",");
            request.setVideoIds(videoIds);

            // System.out.println(id);
            // 调用方法删除
            if (!StringUtils.isEmpty(videoIds)) {
                client.getAcsResponse(request);
            }


        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(20001, "删除视频失败");
        }
    }

/*    public static void main(String[] args) {
        List<String> list =new ArrayList<>();
        list.add("111");
        list.add("222");
        list.add("333");
        // 111,222,333
        // package org.apache.commons.lang;
        String join = StringUtils.join(list.toArray(),",");
        System.out.println(join);

    }*/
}
