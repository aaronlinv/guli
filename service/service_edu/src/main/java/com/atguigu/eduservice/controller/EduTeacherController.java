package com.atguigu.eduservice.controller;


import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduTeacherService;
import com.atguigu.eduservice.service.impl.EduTeacherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-07-13
 */
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {

    @Autowired
    EduTeacherService teacherService;

    /**查询所有
     * @return 
     */
    @GetMapping("findAll")
    public List<EduTeacher> list() {
        List<EduTeacher> list = teacherService.list(null);
        return list;
    }

    /**
     * 逻辑删除
     * @param id 
     * @return
     */
    @DeleteMapping("{id}")
    public boolean removeTeacher(@PathVariable("id") String id) {
        boolean flag = teacherService.removeById(id);
        return flag;
    }
}

