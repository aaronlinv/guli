package com.atguigu.vod.service;

import org.springframework.web.multipart.MultipartFile;

public interface VodService {
    // 上传视频
    String uploadVideoAly(MultipartFile file);
}
