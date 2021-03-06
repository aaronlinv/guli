package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-07-17
 */
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin    
public class EduCourseController {
    @Autowired
    private EduCourseService courseService;

    // 添加课程信息
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        // 需要返回课程id，前端后续需要通过id 添加课程大纲
        String id = courseService.saveCourseInfo(courseInfoVo);


        return R.ok().data("courseId", id);
    }

    // 根据课程id 查询课程基本信息
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable("courseId") String courseId) {

        CourseInfoVo courseInfoVo = courseService.getCourseInfo(courseId);

        return R.ok().data("courseInfoVo", courseInfoVo);
    }

    // 修改课程信息
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        courseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }
    
    
     // 根据id 查询确认信息
    @GetMapping("getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable("id")String id){
        CoursePublishVo coursePublishVo= courseService.publishCourseInfo(id);
        return R.ok().data("publishCourse",coursePublishVo);
    }
    
    // 课程最终发布
    @PostMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable("id")String id){
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        
        eduCourse.setStatus("Normal");
        
        courseService.updateById(eduCourse);
        return R.ok();
    }
    
    // 课程列表分页查询
    // todo
    
    // 查询所有课程
    @GetMapping
    public R getCourseList(){
        List<EduCourse> list = courseService.list(null);
        return R.ok().data("list",list);
    }
    
    // 删除课程
    @DeleteMapping("{courseId}")
    public R deleteCourse(@PathVariable("courseId")String courseId){
        courseService.removeCourse(courseId);
        return  R.ok(); 
                
    }
    
}

