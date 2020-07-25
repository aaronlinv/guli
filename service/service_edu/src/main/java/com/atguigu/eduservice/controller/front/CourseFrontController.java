package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.frontvo.CourseFromVo;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/eduservice/coursefront")
@CrossOrigin
public class CourseFrontController {
    @Autowired
    private EduCourseService courseService;

    // 按条件查询课程
    @PostMapping("getFrontCourseList/{page}/{limit}")
    public R getFrontCourseList(@PathVariable("page") long page,
                                @PathVariable("limit") long limit,
                                @RequestBody(required = false) CourseFromVo courseFromVo) {


        Page<EduCourse> coursePage = new Page<>(page,limit);
        
        Map<String,Object>map =  courseService.getFrontCourseList(coursePage,courseFromVo);
        return R.ok().data(map);
    }
}
