package com.atguigu.msmservice.service;

import java.util.Map;

public interface MsmService {

    // 发送短信
    boolean send( String phone, Map<String,Object> param);
}
