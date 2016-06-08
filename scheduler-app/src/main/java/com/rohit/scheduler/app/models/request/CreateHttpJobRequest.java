package com.rohit.scheduler.app.models.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rohit.scheduler.app.models.Trigger;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateHttpJobRequest implements Serializable {

    @NotBlank
    String jobName;

    @NotBlank
    String httpCallBackUrl;

    @Valid
    Trigger trigger;

    public Trigger getTrigger() {
        return trigger;
    }

    public void setTrigger(Trigger trigger) {
        this.trigger = trigger;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getHttpCallBackUrl() {
        return httpCallBackUrl;
    }

    public void setHttpCallBackUrl(String httpCallBackUrl) {
        this.httpCallBackUrl = httpCallBackUrl;
    }
}
