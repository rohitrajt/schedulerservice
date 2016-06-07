package com.rohit.scheduler.app.services.impl;

import com.rohit.scheduler.app.services.CallbackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("httpCallBackService")
public class HttpCallbackServiceImpl implements CallbackService {

    private static final Logger logger = LoggerFactory.getLogger(HttpCallbackServiceImpl.class);

    @Override
    public void execute() {

        logger.info("Executing Http call back service");
    }
}
