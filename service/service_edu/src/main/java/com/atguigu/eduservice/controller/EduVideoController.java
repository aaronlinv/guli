package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-07-17
 */
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {
    @Autowired
    private EduVideoService videoService;
    
    // 注入VodClient
    @Autowired
    private VodClient vodClient;
    
    // 添加小节
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
        
        videoService.save(eduVideo);
        return R.ok();
    }
    
    // 删除小节
    // todo 删除小节需要删除对应视频
    @DeleteMapping("/{id}")
    public R deleteVideo(@PathVariable("id")String id){
        
        // 先根据小节id 获取视频id
        EduVideo eduVideo = videoService.getById(id);
        String videoSourceId = eduVideo.getVideoSourceId();
        
        // 为空没有视频，就不要删除
        if(!StringUtils.isEmpty(videoSourceId)){
            // 调用服务 删除阿里云的视频
            vodClient.removeAlyVideo(videoSourceId);    
        }
        
        videoService.removeById(id);
        return R.ok();
    }
    // 修改小节
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo){
        videoService.updateById(eduVideo);
        return R.ok();
    }
}

