package com.example.demo.controller;

import java.text.ParseException;
import java.util.Date;

import com.example.demo.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.common.Result;
import com.example.demo.constant.GloabalConstant;
import com.example.demo.entity.QuartzJobModule;
import com.example.demo.qzComp.TestJob;
import com.example.demo.utils.CronUtil;
import com.example.demo.utils.QuartzJobComponent;

/**
 * @Description: JobController
 * @author: lixl
 * @Date: 2020/9/18 16:19
 */ 
@RestController
@RequestMapping("/job")
public class JobController {
    private final static Logger LOGGER = LoggerFactory.getLogger(JobController.class);

    @Autowired
    private QuartzJobComponent quartzJobComponent;

    @ResponseBody
    @PostMapping("/add")
    public Result save(String jobName, String cron) {
        LOGGER.info("新增任务");
        try {
            QuartzJobModule quartzJobModule = new QuartzJobModule();
            Date startDate = DateUtils.string2Date("20200912");
            Date endDate = DateUtils.string2Date("202001012");
            quartzJobModule.setStartTime(CronUtil.getStartDate(startDate));
            quartzJobModule.setEndTime(CronUtil.getEndDate(endDate));
            // 注意：在后面的任务中需要通过这个JobName来获取你要处理的数据，因此您可以讲这个设置为你要处理的数据的主键，比如id
            quartzJobModule.setJobName(jobName);
            quartzJobModule.setTriggerName("tesTriggerNmae");
            quartzJobModule.setJobGroupName(GloabalConstant.QZ_JOB_GROUP_NAME);
            quartzJobModule.setTriggerGroupName(GloabalConstant.QZ_TRIGGER_GROUP_NAME);
            quartzJobModule.setCron(cron);
            quartzJobModule.setJobClass(TestJob.class);
            quartzJobComponent.addJob(quartzJobModule);
        }
        catch (Exception e) {
            e.printStackTrace();
            return Result.ok();
        }
        return Result.ok();
    }

    @PostMapping("/edit")
    @ResponseBody
    public Result edit(String jobName) {
        try {
            LOGGER.info("编辑任务");
            Date startDate = DateUtils.string2Date("20200912");
            Date endDate = DateUtils.string2Date("202001012");
            // "testJobId"为add方法添加的job的name
            quartzJobComponent.modifyJobTime(jobName, "0/10 *  * ? * *", startDate, endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Result.ok();
    }

    @PostMapping("/pause")
    @ResponseBody
    public Result pause(String jobName, String jobGroup) {
        LOGGER.info("停止任务");
        quartzJobComponent.pauseJob(jobName);
        return Result.ok();
    }

    @PostMapping("/resume")
    @ResponseBody
    public Result resume(String jobName, String jobGroup) {
        LOGGER.info("恢复任务");
        quartzJobComponent.resumeJob(jobName);
        return Result.ok();

    }

    @PostMapping("/remove")
    @ResponseBody
    public Result remove(String jobName, String jobGroup) {
        LOGGER.info("移除任务");
        quartzJobComponent.removeJob(jobName);
        return Result.ok();
    }
}
