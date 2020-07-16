package com.atguigu.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.service.EduSubjectService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {
    // SubjectExcelListener 不能交给Spring管理，需要手动new 不能注入
    // 不能实现数据库操作

    private EduSubjectService subjectService;

    public SubjectExcelListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }

    public SubjectExcelListener() {
    }

    // 一行一行读取excel ，每次读取一级分类和二级分类
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if (subjectData == null) {
            throw new GuliException(20001, "文件数据为空");
        }


        // 判断一级分类不能重复添加
        EduSubject existOneSubject = this.existOneSubject(subjectService, subjectData.getOneSubjectName());
        if (existOneSubject == null) { //没有相同 添加
            existOneSubject = new EduSubject();
            existOneSubject.setParentId("0");
            existOneSubject.setTitle(subjectData.getOneSubjectName());
            
            subjectService.save(existOneSubject);
        }
        
        // 一级分类id值
        String pid = existOneSubject.getId();

        EduSubject existTwoSubject = this.existTwoSubject(subjectService, subjectData.getTwoSubjectName(), pid);
        // 判断二级分类不能重复添加
        if (existTwoSubject == null) { //没有相同 添加
            existTwoSubject = new EduSubject();
            existTwoSubject.setParentId(pid);
            // getTwoSubjectName注意
            existTwoSubject.setTitle(subjectData.getTwoSubjectName());

            subjectService.save(existTwoSubject);
        }
    }

    /**
     * 判断一级分类不能重复添加
     *
     * @param name
     * @return
     */
    private EduSubject existOneSubject(EduSubjectService subjectService,String name) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", "0");

        EduSubject oneSubject = subjectService.getOne(wrapper);

        return oneSubject;

    }


    /**
     * 判断二级分类不能重复添加
     * @param name 
     * @return
     */
    private EduSubject existTwoSubject(EduSubjectService subjectService,String name,String pid) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", pid);
        
        EduSubject twoSubject = subjectService.getOne(wrapper);

        return twoSubject;

    }
    
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
