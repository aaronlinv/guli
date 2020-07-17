package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-07-16
 */
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {
    @Autowired
    private EduSubjectService subjectService;
    
    
    // 添加课程
    // 获取上传的文件，读取内容
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file){
        // 上传过的文件
        subjectService.saveSubject(file,subjectService);
        
        return R.ok();
    }
    
    // 课程分类列表
    @GetMapping("getAllSubject")
    public R getAllSubject(){
        
        return R.ok();
    }
            
    
}

