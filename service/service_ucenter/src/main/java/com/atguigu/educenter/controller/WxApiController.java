package com.atguigu.educenter.controller;

import com.atguigu.commonutils.R;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.service.UcenterMemberService;
import com.atguigu.educenter.utils.ConstantWxUtils;
import com.atguigu.educenter.utils.HttpClientUtils;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;
import java.util.HashMap;

@Controller
@CrossOrigin
@RequestMapping("/api/ucenter/wx")
public class WxApiController {
    @Autowired
    private UcenterMemberService memberService;

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
        } catch (Exception e) {
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
    public String callback(String code, String state) {
        try {
            // 1. code 临时票据
            System.out.println("code == >" + code);
            System.out.println("state == >" + state);

            // 2. 向认证服务器发送请求换取access_token
            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";

            String accessTokenUrl = String.format(baseAccessTokenUrl,
                    ConstantWxUtils.WX_OPEN_APP_ID,
                    ConstantWxUtils.WX_OPEN_APP_SECRET,
                    code);

            // 获取Json
            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl);
            System.out.println("accessTokenInfo == >" + accessTokenInfo);

            Gson gson = new Gson();


            HashMap mapAccessToken = gson.fromJson(accessTokenInfo, HashMap.class);
            String accessToken = (String) mapAccessToken.get("access_token");
            String openId = (String) mapAccessToken.get("open_id");
         


            // 存入数据库，先判断是否存在
            UcenterMember member = memberService.getOpenIdMember(openId);

            if (member == null) {
                // 不存在该用户

                // 3.访问微信的资源服务器，获取用户信息
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                String userInfoUrl = String.format(baseUserInfoUrl,
                        accessToken,
                        openId);
                String userInfo = HttpClientUtils.get(userInfoUrl);
                System.out.println("userInfo == >" + userInfo);

                HashMap userInfoMap = gson.fromJson(userInfo, HashMap.class);
                String nickname = (String) userInfoMap.get("nickname");
                String headimgurl = (String) userInfoMap.get("headimgurl");
                
                
                member = new UcenterMember();
                member.setOpenid(openId);
                member.setAvatar(headimgurl);
                member.setNickname(nickname);
                memberService.save(member);

            }
            
            
            
            return "redirect:http://localhost:3000";

        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(20001, "微信登录失败");
        }

    }
}
