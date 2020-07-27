package com.atguigu.eduorder.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.eduorder.entity.Order;
import com.atguigu.eduorder.service.OrderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-07-27
 */
@RestController
@RequestMapping("/eduorder/order")
@CrossOrigin
public class OrderController {
    @Autowired
    private OrderService orderService;
    
    // 生成订单
    @PostMapping("createOrder/{courseId}")
    public R createOrder(@PathVariable("courseId")String courseId,
                         HttpServletRequest request){
        String id = JwtUtils.getMemberIdByJwtToken(request);
        // 返回订单号
        String orderNo = orderService.createOrders(courseId,id);
        
        return R.ok().data("orderId",orderNo);
    }
    
    // 根据订单id 查询订单信息
    @GetMapping("getOrderInfo/{orderId}")
    public R getOrderInfo(@PathVariable("orderId")String orderId){
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no", orderId);
        Order order = orderService.getOne(queryWrapper);
        return R.ok().data("item",order);
    }

}

