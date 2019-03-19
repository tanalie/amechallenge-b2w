package com.jhonatansouza.exceptions;

public class SwapiException extends Exception {

    public SwapiException() {
    }

    public SwapiException(String message) {
        super(message);
    }

    public SwapiException(String message, Throwable cause) {
        super(message, cause);
    }

    public SwapiException(Throwable cause) {
        super(cause);
    }

    public SwapiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
