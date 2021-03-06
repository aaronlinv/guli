package com.atguigu.vodtest;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;

import java.util.List;

public class TestVod {
    public static void main(String[] args) throws Exception {
        // 根据视频id 获取播放地址
        // getPlayUrl();

        DefaultAcsClient client = InitObject.initVodClient();
        
        // 创建获取视频凭证的request和response
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
        
        // 设置视频id值
        request.setVideoId(id);
        // 得到凭证
        response = client.getAcsResponse(request);
        System.out.println("getPlayAuth ==>"+response.getPlayAuth());
        
        
        
    }
    // 根据视频id 获取播放地址
    private static void getPlayUrl() throws ClientException {
        


        // 1.初始化对象
        DefaultAcsClient client = InitObject.initVodClient();
        // 2.创建获取视频地址的request和response
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();

        // 3.向request对象设置视频id
        request.setVideoId(id);

        // 传递request获取数据
        response = client.getAcsResponse(request);


        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        //播放地址
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
        }
        //Base信息
        System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
    }

}
