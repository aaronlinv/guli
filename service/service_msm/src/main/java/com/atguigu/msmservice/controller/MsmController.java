package com.atguigu.msmservice.controller;

import com.atguigu.commonutils.R;
import com.atguigu.msmservice.service.MsmService;
import com.atguigu.msmservice.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/edumsm/msm")
@CrossOrigin
public class MsmController {
    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    
    // 发送短信
    @GetMapping("send/{phone}")
    public R sendMsm(@PathVariable("phone") String phone) {
        // 从Redis中取验证码
        String code = redisTemplate.opsForValue().get(phone);
        if(!StringUtils.isEmpty(code)){
            return R.ok();
        }
        
        // 不存在验证码 通过阿里云发送
        code = RandomUtil.getFourBitRandom();
        Map<String, Object> param = new HashMap<>();
        param.put("code", code);
        
        // 调用service
        boolean isSend = msmService.send( phone,param);
        if (isSend) {
            // 发送成功把验证码放入Redis 设置有效时间 
            // 5分分钟
            
            redisTemplate.opsForValue().set(phone, code,5, TimeUnit.MINUTES);
            
            
            
            return R.ok();
        }else{
            return R.error().message("短信发送失败");
        }
    }
}
