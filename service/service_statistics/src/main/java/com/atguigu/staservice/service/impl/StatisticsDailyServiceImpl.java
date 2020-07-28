package com.atguigu.staservice.service.impl;

import com.atguigu.commonutils.R;
import com.atguigu.staservice.client.UcenterClient;
import com.atguigu.staservice.entity.StatisticsDaily;
import com.atguigu.staservice.mapper.StatisticsDailyMapper;
import com.atguigu.staservice.service.StatisticsDailyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-07-28
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {
    // 远程调用Ucenter
    @Autowired 
    private UcenterClient ucenterClient;
    
    @Override
    public void registerCount(String day) {
        // 保证每一天只有一条数据，删除已存在的数据
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated", day);
        baseMapper.delete(wrapper);
                
        
        R registerR = ucenterClient.countRegister(day);
        Map<String, Object> data = registerR.getData();
        Integer countRegister = (Integer) data.get("countRegister");
        // 添加数据到数据库
        StatisticsDaily daily = new StatisticsDaily();
        // 注册人数
        daily.setRegisterNum(countRegister);
        // 统计日期
        daily.setDateCalculated(day);

        //import org.apache.commons.lang3.RandomUtils;
        Integer loginNum = RandomUtils.nextInt(100, 200);//TODO
        Integer videoViewNum = RandomUtils.nextInt(100, 200);//TODO
        Integer courseNum = RandomUtils.nextInt(100, 200);//TODO
        
        //创建统计对象
        daily.setLoginNum(loginNum);
        daily.setVideoViewNum(videoViewNum);
        daily.setCourseNum(courseNum);

        baseMapper.insert(daily);
    }
}
