package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.service.EduChapterService;
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
@RequestMapping("/eduservice/chapter")
@CrossOrigin
public class EduChapterController {

    @Autowired
    private EduChapterService chapterService;

    // 返回课程大纲列表
    // 根据课程id查询
    @GetMapping("getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable("courseId") String courseId) {


        List<ChapterVo> list =chapterService.getChapterVideoByCourseId(courseId);
        
        return R.ok().data("allChapterVideo",list);
    }


}

