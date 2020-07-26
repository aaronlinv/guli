package com.atguigu.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.atguigu.commonutils.R;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.atguigu.vod.service.VodService;
import com.atguigu.vod.utils.InitVodClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.ws.rs.GET;
import java.util.List;

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
            throw new GuliException(20001, "删除视频失败");
        }
    }

    // 删除多个阿里视频
    @DeleteMapping("delete-batch")
    // 传入多个id
    public R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList) {
        vodService.removeMoreAlyVideo(videoIdList);

        return R.ok();
    }

    // 根据视频id获取视频播放凭证
    @GetMapping("getPlayAuth/{id}")
    public R getPlayAuth(@PathVariable("id") String id) {
        try {
            DefaultAcsClient client = InitVodClient.initVodClient();
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();

            // 向request设置id
            request.setVideoId(id);

            // 调用方法得到凭证
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return R.ok().data("playAuth",playAuth);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(20001, "获取凭证失败");

        }


    }

}
