package com.rohit.scheduler.app.error;

import org.omg.SendingContext.RunTime;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class RestControllerErrorHandler {

    private static final Logger logger = LoggerFactory.getLogger(RestControllerErrorHandler.class);

    @ExceptionHandler(value = {RuntimeException.class, Exception.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse handleInternalServerError(HttpServletRequest request, Exception e) {

        logger.error("There was an internal server error", e);

        return new ErrorResponse("There was an error processing your request", HttpStatus.INTERNAL_SERVER_ERROR.toString());
    }

    @ExceptionHandler(value = {BadRequestException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleBadRequestException(HttpServletRequest request, Exception e) {

        logger.error("Bad Request", e);

        return new ErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.toString());
    }
}
