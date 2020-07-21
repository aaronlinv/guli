package com.atguigu.educenter.controller;


import com.atguigu.commonutils.R;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.atguigu.educenter.service.UcenterMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    
}

