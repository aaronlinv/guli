package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/teacherfront")
@CrossOrigin
public class TeacherFrontController {
    @Autowired
    private EduTeacherService teacherService;
    
    @Autowired
    private EduCourseService courseService;
    
    // 分页查询讲师
    @PostMapping("getTeacherFrontList/{page}/{limit}")
    public R getTeacherFrontList(@PathVariable("page") long page,
                                 @PathVariable("limit") long limit){
        Page<EduTeacher> pageParam = new Page<>(page,limit);
        
        Map<String,Object> map = teacherService.getTeacherFrontList(pageParam);
        
        // 返回分页数据
        return R.ok().data(map);
    }
    @GetMapping("getTeacherFrontInfo/{teacherId}")
    public R getTeacherFrontInfo(@PathVariable("teacherId")String teacherId){
        
        // 1. 查询讲师基本信息
        EduTeacher eduTeacher = teacherService.getById(teacherId);
        // 2.查询课程信息
        QueryWrapper<EduCourse> courseQueryWrapper =  new QueryWrapper<>();
        courseQueryWrapper.eq("teacher_id", teacherId);
        List<EduCourse> courseList = courseService.list(courseQueryWrapper);
        
        return R.ok().data("teacher",eduTeacher).data("courseList",courseList);
    }
}
