package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/eduservice/teacherfront")
@CrossOrigin
public class TeacherFrontController {
    @Autowired
    private EduTeacherService teacherService;
    
    // 分页查询讲师
    @PostMapping("getTeacherFrontList/{page}/{limit}")
    public R getTeacherFrontList(@PathVariable("page") long page,
                                 @PathVariable("limit") long limit){
        Page<EduTeacher> pageParam = new Page<>(page,limit);
        
        Map<String,Object> map = teacherService.getTeacherFrontList(pageParam);
        
        // 返回分页数据
        return R.ok().data(map);
    }
}
