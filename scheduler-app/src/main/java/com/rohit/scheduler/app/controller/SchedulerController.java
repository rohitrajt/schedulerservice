package com.rohit.scheduler.app.controller;

import com.rohit.scheduler.app.error.BadRequestException;
import com.rohit.scheduler.app.jobs.HttpCallbackJob;
import com.rohit.scheduler.app.models.request.CreateHttpJobRequest;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

@RestController
@RequestMapping(value = "/scheduler")
public class SchedulerController {

    private static final Logger logger = LoggerFactory.getLogger(SchedulerController.class);

    private static final String HTTP_TRIGGER_GROUP = "httpTriggerGroup";
    private static final String HTTP_JOB_GROUP = "httpJobGroup";
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    @Autowired
    Scheduler scheduler;

    @RequestMapping(value = "/addJob", method = RequestMethod.POST)
    @ResponseBody
    public void addJob(@RequestBody CreateHttpJobRequest createHttpJobRequest) {
        try {
            logger.info("Scheduling job with name {}", createHttpJobRequest.getJobName());

            //Create the job detail
            JobDetail jobDetail = newJob(HttpCallbackJob.class)
                    .storeDurably(true)
                    .withIdentity(createHttpJobRequest.getJobName() + "-" + UUID.randomUUID(), HTTP_JOB_GROUP).build();

            String triggerName = createHttpJobRequest.getJobName() + "-trigger" + UUID.randomUUID();

            SimpleDateFormat dateTimeFormatter = new SimpleDateFormat(DATE_TIME_FORMAT);
            Date date = dateTimeFormatter.parse(createHttpJobRequest.getTrigger().getDateTimeToExecute());

            //Create the trigger
            Trigger trigger = newTrigger()
                    .withIdentity(triggerName, HTTP_TRIGGER_GROUP)
                    .startAt(date).build();

            scheduler.scheduleJob(jobDetail, trigger);
        } catch (ParseException e) {
            throw new BadRequestException(e);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }

    }

    @RequestMapping(value = "/deleteJob", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteJob(@RequestParam String name) throws SchedulerException {
        scheduler.deleteJob(new JobKey(name, HTTP_JOB_GROUP));
    }
}
