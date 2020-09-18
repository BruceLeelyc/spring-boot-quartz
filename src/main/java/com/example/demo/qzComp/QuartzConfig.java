package com.example.demo.qzComp;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: QuartzConfig
 * @author: lixl
 * @Date: 2020/9/18 17:14
 */ 
@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail testJob(){
        JobDetail jobDetail = JobBuilder.newJob(TestJob.class)
                .withIdentity("myJob","myJobGroup")
                //JobDataMap可以给任务execute传递参数
                .usingJobData("job_param","job_param")
                .storeDurably()
                .build();
        return jobDetail;
    }
    @Bean
    public Trigger myTrigger(){
        Trigger trigger = TriggerBuilder.newTrigger()
                .forJob(testJob())
                .withIdentity("myTrigger","myTriggerGroup")
                .usingJobData("job_trigger_param","job_trigger_param")
                .startNow()
                //.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever())
                .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ? "))
                .build();
        return trigger;
    }
}
