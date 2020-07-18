package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduCourseDescription;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.mapper.EduCourseMapper;
import com.atguigu.eduservice.service.EduCourseDescriptionService;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-07-17
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    EduCourseDescriptionService courseDescriptionService;
    

    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        // 添加基本信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);

        int insert = baseMapper.insert(eduCourse);
        // 影响行数
        if (insert <= 0) {
            // 添加异常
            throw new GuliException(20001, "添加课程信息失败");
        }
        
        // 获取course id
        String cid = eduCourse.getId();
        
        // 添加简介信息
        EduCourseDescription courseDescription =new EduCourseDescription();
        courseDescription.setDescription(courseInfoVo.getDescription());
        // 设置cid
        courseDescription.setId(cid);
        courseDescriptionService.save(courseDescription);
        
        return cid;
    }

    /**
     * 根据课程id 查询课程基本信息
     * @param courseId 
     * @return
     */
    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        
        // 课程信息
        EduCourse eduCourse = baseMapper.selectById(courseId);
        BeanUtils.copyProperties(eduCourse, courseInfoVo);
        
        // 描述信息
        EduCourseDescription courseDescription = courseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(courseDescription.getDescription());
        
        
        return courseInfoVo;
    }
}
