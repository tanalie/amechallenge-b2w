package com.jhonatansouza.error;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class ErrorHandler {

    private int status;
    private String message;
    private LocalDateTime timeStamp;

    public ErrorHandler(int status, String message) {
        this.status = status;
        this.message = message;
        this.timeStamp  = LocalDateTime.now(ZoneId.systemDefault());
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public String getTimeStamp() {
        return this.timeStamp.toString();
    }

}
