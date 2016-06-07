package com.rohit.scheduler.app.jobs;

import com.rohit.scheduler.app.services.CallbackService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class HttpCallbackJob implements Job {

    private static final Logger logger = LoggerFactory.getLogger(HttpCallbackJob.class);

    @Autowired
    @Qualifier(value = "httpCallBackService")
    CallbackService callbackService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        logger.info("Executing Job http call back");

        callbackService.execute();
    }
}
