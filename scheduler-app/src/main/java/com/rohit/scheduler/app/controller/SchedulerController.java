package com.rohit.scheduler.app.controller;

import com.rohit.scheduler.app.jobs.HttpCallbackJob;
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

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public void addJob() {
        logger.info("Scheduling a job");

        JobDetail jobDetail = newJob(HttpCallbackJob.class)
                .withIdentity("myjob", "group1").build();

        Trigger trigger = newTrigger()
                .withIdentity("mytrigger", "group1")
                .startNow()
                .withSchedule(simpleSchedule().withIntervalInSeconds(30).repeatForever()).build();

        try {
            scheduler.scheduleJob(jobDetail,trigger);
        } catch (SchedulerException e) {
            logger.error("Error while trrying to schedule a job", e);
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteJob(@RequestParam String name, @RequestParam  String group) {
        try {
            scheduler.deleteJob(new JobKey(name, group));
        } catch (SchedulerException e) {
            logger.error("Error while trying to delete");
        }
    }
}
