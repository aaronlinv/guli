package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.entity.subject.TwoSubject;
import com.atguigu.eduservice.listener.SubjectExcelListener;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-07-16
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    /**
     * 添加课程分类
     *
     * @param file
     * @param subjectService
     */
    @Override
    public void saveSubject(MultipartFile file, EduSubjectService subjectService) {
        try {
            InputStream in = file.getInputStream();
            // 注意!! (流，excel对应的实体类，Listener)
            // 没有传入excel对应实体类报错
            // com.alibaba.excel.exception.ExcelAnalysisException:  
            // java.util.LinkedHashMap cannot be cast to com.al.eduservice.entity.excel.SubjectData
            EasyExcel.read(in, SubjectData.class, new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (Exception e) {

        }


    }

    /**
     * 获取树形的课程分类列表
     *
     * @return
     */
    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        // 查询一级分类
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id", "0");
        List<EduSubject> oneSubjectList = baseMapper.selectList(wrapperOne);

        // 查询二级分类
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id", "0");
        List<EduSubject> twoSubjectList = baseMapper.selectList(wrapperTwo);

        // 最终集合
        List<OneSubject> finalSubjectList = new ArrayList<>();


        // 封装一级分类
        for (EduSubject eduSubject : oneSubjectList) {
            OneSubject oneSubject = new OneSubject();
            // oneSubject.setId(eduSubject.getId());
            // oneSubject.setTitle(eduSubject.getTitle());


            // Spring包里
            BeanUtils.copyProperties(eduSubject, oneSubject);

            // 封装二级分类
            List<TwoSubject> twoFinalSubjectList = new ArrayList<>();
            for (EduSubject tSubject : twoSubjectList) {
                if (tSubject.getParentId().equals(eduSubject.getId())) {
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(tSubject, twoSubject);

                    twoFinalSubjectList.add(twoSubject);
                }


            }
            oneSubject.setChildren(twoFinalSubjectList);
            finalSubjectList.add(oneSubject);
        }


        return finalSubjectList;
    }
}
