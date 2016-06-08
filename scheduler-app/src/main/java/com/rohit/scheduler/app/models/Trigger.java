package com.rohit.scheduler.app.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Trigger {

    String dateTimeToExecute;

    public String getDateTimeToExecute() {
        return dateTimeToExecute;
    }

    public void setDateTimeToExecute(String dateTimeToExecute) {
        this.dateTimeToExecute = dateTimeToExecute;
    }
}
