package com.atguigu.educenter.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.commonutils.ordervo.UcenterMemberOrder;
import com.atguigu.commonutils.vo.UcenterMemberPay;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.atguigu.educenter.service.UcenterMemberService;
import io.swagger.models.auth.In;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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
    public R login(@RequestBody UcenterMember member) {

        String token = memberService.login(member);
        return R.ok().data("token", token);
    }

    // 注册
    @PostMapping("register")
    public R registerUser(@RequestBody RegisterVo registerVo) {
        memberService.register(registerVo);
        return R.ok();
    }

    // 根据token获取用户id
    @GetMapping("getMemberInfo")
    public R getMemberInfo(HttpServletRequest request) {
        // 调用jwt工具类方法，根据request对象获取头信息，返回用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        // 查询数据库
        UcenterMember member = memberService.getById(memberId);
        return R.ok().data("userInfo", member);
    }

    // 根据id查询用户
    @GetMapping("getInfoUc/{id}")
    public UcenterMemberPay getMemberInfo(@PathVariable("id") String memberId) {

        UcenterMember member = memberService.getById(memberId);
        UcenterMemberPay ucenterMemberPay = new UcenterMemberPay();
        BeanUtils.copyProperties(member, ucenterMemberPay);
        return ucenterMemberPay;
    }

    // 根据用户id查询用户 order模块远程调用
    @PostMapping("getUserInfoOrder/{id}")
    public UcenterMemberOrder getUserInfoOrder(@PathVariable("id") String id) {
        UcenterMember member = memberService.getById(id);

        UcenterMemberOrder ucenterMemberOrder = new UcenterMemberOrder();
        BeanUtils.copyProperties(member, ucenterMemberOrder);
        return ucenterMemberOrder;  

    }
    
    // 查询某一天的注册人数
    @GetMapping("countRegister/{day}")
    public R  countRegister(@PathVariable("day")String day){
        Integer count = memberService.countRegister(day);
        return R.ok().data("countRegister",count);
    }

}

