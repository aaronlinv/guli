package com.atguigu.staservice.schedule;

import com.atguigu.staservice.service.StatisticsDailyService;
import com.atguigu.staservice.utils.DateUtil;
import org.apache.catalina.core.StandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Component
public class ScheduledTask {
    @Autowired
    private  StatisticsDailyService staService; 
    
    // 每隔5s 执行一次
    // @Scheduled(cron = "0/5 * * * * ?")
    
    // 每天1点执行 统计前一天的数据
    @Scheduled(cron = "0 0 1 * * ?")
    public void task1(){
        // System.out.println("***************tast1*******************");
        
        
        // 统计前一天的数据
        String day = DateUtil.formatDate(DateUtil.addDays(new Date(), -1));
        staService.registerCount(day);
        
    }
}
