package com.rohit.scheduler.app.models.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateHttpJobRequest implements Serializable {

    @NotBlank
    String jobName;

    @NotBlank
    String jobGroup;

    String httpCallBackUrl;


    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getHttpCallBackUrl() {
        return httpCallBackUrl;
    }

    public void setHttpCallBackUrl(String httpCallBackUrl) {
        this.httpCallBackUrl = httpCallBackUrl;
    }
}
