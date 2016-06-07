package com.rohit.scheduler.app.error;

import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class RestControllerErrorHandler {

    private static final Logger logger = LoggerFactory.getLogger(RestControllerErrorHandler.class);

    @ExceptionHandler(value = {SchedulerException.class, Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleInternalServerError(HttpServletRequest request, Exception e) {

        logger.error("There was an internal server error", e);

        return new ErrorResponse("There was an error processing your request", HttpStatus.INTERNAL_SERVER_ERROR.toString());
    }
}
