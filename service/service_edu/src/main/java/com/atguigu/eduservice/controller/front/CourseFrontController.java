package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.commonutils.ordervo.CourseWebVoOrder;
import com.atguigu.eduservice.client.OrdersClient;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.entity.frontvo.CourseFromVo;
import com.atguigu.eduservice.entity.frontvo.CourseWebVo;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/coursefront")
@CrossOrigin
public class CourseFrontController {
    @Autowired
    private EduCourseService courseService;
    @Autowired
    private EduChapterService chapterService;
    @Autowired
    private OrdersClient ordersClient;

    // 按条件查询课程
    @PostMapping("getFrontCourseList/{page}/{limit}")
    public R getFrontCourseList(@PathVariable("page") long page,
                                @PathVariable("limit") long limit,
                                @RequestBody(required = false) CourseFromVo courseFromVo) {


        Page<EduCourse> coursePage = new Page<>(page,limit);
        
        Map<String,Object>map =  courseService.getFrontCourseList(coursePage,courseFromVo);
        return R.ok().data(map);
    }
    // 课程详情页面 查询
    // 多表查询
    @GetMapping("getFrontCourseInfo/{id}")
    public R getFrontCourseInfo(@PathVariable("id")String courseId, HttpServletRequest request){
        // 编写sql 查询课程信息
        CourseWebVo courseWebVo = courseService.getBaseCourseInfo(courseId);
        
        // 查询课程小节信息
        List<ChapterVo> chapterVideoList = chapterService.getChapterVideoByCourseId(courseId);

        // 根据课程id 和用户id 查询课程 购买情况
        
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        System.out.println("courseId == >"+courseId);
        System.out.println("memberId == >"+memberId);
        
        boolean isBuy = ordersClient.isBuyCourse(courseId, memberId);
        
        System.out.println("isBuy == >"+isBuy);
        return R.ok().data("courseWebVo",courseWebVo).data("chapterVideoList",chapterVideoList)
                .data("isBuy",isBuy);
    }
    // 根据课程信息查询课程
    @PostMapping("getCourseInfoOrder/{id}")
    public CourseWebVoOrder getCourseInfoOrder(@PathVariable("id")String id){
        CourseWebVo baseCourseInfo = courseService.getBaseCourseInfo(id);
        CourseWebVoOrder courseWebVoOrder = new CourseWebVoOrder();
        BeanUtils.copyProperties(baseCourseInfo,courseWebVoOrder);
        
        return courseWebVoOrder;
    }
}
