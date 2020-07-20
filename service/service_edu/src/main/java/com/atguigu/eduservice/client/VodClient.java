package com.atguigu.eduservice.client;

import com.atguigu.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

// 要调用的服务名称

@FeignClient(name = "service-vod",fallback = VodFileDegradeFeignClient.class)
@Component
public interface VodClient {
    // 定义调用方法的路径
    
    // 注意要完全路径 
    @DeleteMapping("/eduvod/video/removeAlyVideo/{id}")
    public R removeAlyVideo(@PathVariable("id") String id);

    // 删除多个阿里视频
    @DeleteMapping("/eduvod/video/delete-batch")
    // 传入多个id
    public R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList);
    
    
}
