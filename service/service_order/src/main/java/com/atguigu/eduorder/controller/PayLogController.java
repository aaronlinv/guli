package com.atguigu.eduorder.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduorder.service.PayLogService;
import org.apache.poi.ss.formula.functions.Odd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-07-27
 */
@RestController
@RequestMapping("/eduorder/paylog")
@CrossOrigin
public class PayLogController {
    @Autowired
    private PayLogService payLogService;
    
    // 生成微信支付二维码
    @GetMapping("createNative/{orderNo}")
    public R createNative(@PathVariable("orderNo")String orderNo){
        
        Map map= payLogService.createNative(orderNo);
        System.out.println("生成二维码map == >" +map);
        return R.ok().data("map",map);
    }
    
    // 根据订单号查询订单支付状态
    @GetMapping("queryPayStatus/{orderNo}")
    public R queryPayStatus(@PathVariable("orderNo")String orderNo){
        Map<String,String> map = payLogService.queryPayStatus(orderNo);
        System.out.println("查询订单支付状态map == >" +map);
        if(map==null){
            return R.error().message("支付出错");
        }
        if(map.get("trade_state").equals("SUCCESS")){
            // 添加记录到支付表，更新订单状态
            payLogService.updateOrdersStatus(map);
            return R.ok().message("支付成功");
        }
        // 前端response拦截：25000 表示支付中
        return R.ok().message("支付中").code(25000);
        
    }
}

