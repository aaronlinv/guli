package com.atguigu.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.atguigu.commonutils.R;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.atguigu.vod.service.VodService;
import com.atguigu.vod.utils.InitVodClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduvod/video")
@CrossOrigin
public class VodController {
    @Autowired
    private VodService vodService;

    // 上传视频
    @PostMapping("uploadAlyiVideo")
    public R uploadAlyiVideo(MultipartFile file) {
        String videoId = vodService.uploadVideoAly(file);

        return R.ok().data("videoId", videoId);
    }

    // 根据视频id 删除阿里视频
    @DeleteMapping("removeAlyVideo/{id}")
    public R removeAlyVideo(@PathVariable("id") String id) {
        try {
            DefaultAcsClient client = InitVodClient.initVodClient();
            // 删除视频request
            DeleteVideoRequest request = new DeleteVideoRequest();
            // 设置视频id
            request.setVideoIds(id);
            System.out.println(id);
            // 调用方法删除
            client.getAcsResponse(request);

            return R.ok();

        } catch (Exception e) {
            e.printStackTrace();
            throw  new GuliException(20001, "删除视频失败"); 
        }
    }
}
