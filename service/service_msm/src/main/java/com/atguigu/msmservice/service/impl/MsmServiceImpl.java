package com.atguigu.msmservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.atguigu.msmservice.service.MsmService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service
public class MsmServiceImpl implements MsmService {
    // 发送短信
    @Override
    public boolean send(String phone, Map<String, Object> param) {


        if (StringUtils.isEmpty(phone)) return false;
        //todo
        DefaultProfile profile =
                DefaultProfile.getProfile("default", 
                        ,
                        );
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();

        // 设置固定参数
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");

        // 设置相关参数
        request.putQueryParameter("PhoneNumbers", phone);
        // 阿里云申请的签名
        request.putQueryParameter("SignName", );
        // 阿里云申请的模板
        request.putQueryParameter("TemplateCode",
                );

        // 验证码要转换成Json
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param));


        try {
            CommonResponse response = client.getCommonResponse(request);
            boolean success = response.getHttpResponse().isSuccess();
            System.out.println(" == >");
            System.out.println(phone);
            System.out.println(param);
            return success;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
