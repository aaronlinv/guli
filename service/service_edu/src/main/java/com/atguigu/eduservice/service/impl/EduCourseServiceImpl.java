package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduCourseDescription;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.mapper.EduCourseMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseDescriptionService;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduVideoService;
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
    private  EduCourseDescriptionService courseDescriptionService;
    @Autowired
    private EduVideoService eduVideoService;
    @Autowired
    private EduChapterService chapterService;

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

    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        // 修改课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);

        int update = baseMapper.updateById(eduCourse);
        if(update == 0){
            throw  new GuliException(20001, "修改课程信息失败");
        }
        // 修改描述表
        EduCourseDescription description = new EduCourseDescription();
        description.setId(courseInfoVo.getId());
        description.setDescription(courseInfoVo.getDescription());

        courseDescriptionService.updateById(description);


    }

    @Override
    public CoursePublishVo publishCourseInfo(String id) {
        CoursePublishVo publishInfo = baseMapper.getPublishInfo(id);
        return publishInfo;
    }

    // 删除课程
    @Override
    public void removeCourse(String courseId) {
        // 1.删除小节
        eduVideoService.removeVideoByCourseId(courseId);
        
        // 2.删除章节
        chapterService.removeChapterByCourseId(courseId);
        // 3.删除描述
        courseDescriptionService.removeById(courseId);
        
        // 4.删除课程本身
        int result = baseMapper.deleteById(courseId);
        if(result==0){
            throw new GuliException(20001, "删除失败");
        }
    }
}
