package com.atguigu.educenter.controller;

import com.atguigu.commonutils.R;
import com.atguigu.educenter.utils.ConstantWxUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;

@Controller
@CrossOrigin
@RequestMapping("/api/ucenter/wx")
public class WxApiController {
    @GetMapping("login")
    public String getWxCode() {
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";


        // 进行编码
        String redirectUrl = ConstantWxUtils.WX_OPEN_REDIRECT_URL;
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "utf-8");
        }catch (Exception e){
            e.printStackTrace();
            
        }


        // 第三个state没什么用
        String url = String.format(
                baseUrl,
                ConstantWxUtils.WX_OPEN_APP_ID,
                redirectUrl,
                "atguigu"

        );


        // 重定向到微信
        return "redirect:" + url;
    }
    @GetMapping("callback")
    public String callback(String code ,String state){
        System.out.println("code == >"+code);
        System.out.println("state == >"+ state);
        
        
        
        
        return "redirect:http://localhost:3000";
    }
}
