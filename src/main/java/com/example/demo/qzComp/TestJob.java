package com.example.demo.qzComp;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.scheduling.quartz.QuartzJobBean;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

/**
 * @Description: TaskJobDetail
 * @author: lixl
 * @Date: 2020/9/18 16:22
 */ 
@Slf4j
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class TestJob extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("job 测试开始时间 = {}", LocalDateTime.now());
        String batchId = context.getJobDetail().getKey().getName();
        log.info("执行的任务id为：[{}]", batchId);
    }
    
}
