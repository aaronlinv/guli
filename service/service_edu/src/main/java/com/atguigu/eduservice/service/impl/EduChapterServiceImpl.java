package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.entity.chapter.VideoVo;
import com.atguigu.eduservice.mapper.EduChapterMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-07-17
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService videoService;

    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        // 1.根据id 查章节
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id", courseId);

        List<EduChapter> eduChapterList = baseMapper.selectList(wrapperChapter);


        // 2.根据章节 查小节
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id", courseId);
        List<EduVideo> eduVideoList = videoService.list(wrapperVideo);


        // 3.遍历章节list 进行封装
        List<ChapterVo> finalList = new ArrayList<>();
        for (EduChapter eduChapter : eduChapterList) {

            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter, chapterVo);


            finalList.add(chapterVo);
            // 4.遍历小节list 进行封装
            List<VideoVo> videoList = new ArrayList<>();

            for (EduVideo eduVideo : eduVideoList) {
                if (eduVideo.getChapterId().equals(eduChapter.getId())) {
                    VideoVo videoVo = new VideoVo();

                    BeanUtils.copyProperties(eduVideo, videoVo);
                    videoList.add(videoVo);
                }

            }
            chapterVo.setChildren(videoList);

        }


        return finalList;
    }

    /**
     * 删除章节，有小节不让删除该章节
     *
     * @param chapterId
     * @return
     */
    @Override
    public boolean deleteChapter(String chapterId) {
        // 查询是否存在小节
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id", chapterId);
        // 只需要知道有无，无需要获取到数据
        int count = videoService.count(wrapper);
        if (count > 0) {
            // 存在小节 不能删除
            throw new GuliException(20001, "存在小节，不能删除该章节");
        } else {
            int result = baseMapper.deleteById(chapterId);
            return result > 0 ;
        }
        
    }
}
