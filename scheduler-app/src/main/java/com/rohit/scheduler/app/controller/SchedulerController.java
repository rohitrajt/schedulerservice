package com.rohit.scheduler.app.controller;

import com.rohit.scheduler.app.jobs.HttpCallbackJob;
import com.rohit.scheduler.app.models.request.CreateHttpJobRequest;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

@RestController
@RequestMapping(value = "/scheduler")
public class SchedulerController {

    private static final Logger logger = LoggerFactory.getLogger(SchedulerController.class);

    @Autowired
    Scheduler scheduler;

    @RequestMapping(value = "/addJob", method = RequestMethod.POST)
    @ResponseBody
    public void addJob(@RequestBody CreateHttpJobRequest createHttpJobRequest) throws SchedulerException {
        logger.info("Scheduling a job");

        JobDetail jobDetail = newJob(HttpCallbackJob.class)
                .withIdentity(createHttpJobRequest.getJobName(), createHttpJobRequest.getJobGroup()).build();

        Trigger trigger = newTrigger()
                .withIdentity("mytrigger", "group1")
                .startNow()
                .withSchedule(simpleSchedule().withIntervalInSeconds(30).repeatForever()).build();

        scheduler.scheduleJob(jobDetail, trigger);
    }

    @RequestMapping(value = "/deleteJob", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteJob(@RequestParam String name, @RequestParam String group) throws SchedulerException {
        scheduler.deleteJob(new JobKey(name, group));
    }
}
