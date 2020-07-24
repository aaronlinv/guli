package com.atguigu.educenter.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.atguigu.educenter.service.UcenterMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-07-21
 */
@RestController
@RequestMapping("/educenter/member")
@CrossOrigin

public class UcenterMemberController {
    @Autowired 
    private UcenterMemberService memberService;
    

    // 登录
    @PostMapping("login")
    public R login(@RequestBody UcenterMember member){
        
        String token = memberService.login(member);
        return R.ok().data("token",token);
    }
    
    // 注册
    @PostMapping("register")
    public R registerUser(@RequestBody RegisterVo registerVo){
        memberService.register(registerVo);
        return R.ok();
    }
    // 根据token获取用户id
    @GetMapping("getMemberInfo")
    public R getMemberInfo(HttpServletRequest request){
        // 调用jwt工具类方法，根据request对象获取头信息，返回用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        // 查询数据库
        UcenterMember member = memberService.getById(memberId);
        return  R.ok().data("userInfo",member);
    }
}

