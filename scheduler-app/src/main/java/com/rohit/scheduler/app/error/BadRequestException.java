package com.rohit.scheduler.app.error;

public class BadRequestException  extends RuntimeException {

    private Throwable ex;

    public BadRequestException(Throwable ex) {
        super(ex);
        this.ex = ex;
    }

    public Throwable getEx() {
        return ex;
    }

    public void setEx(Throwable ex) {
        this.ex = ex;
    }
}
